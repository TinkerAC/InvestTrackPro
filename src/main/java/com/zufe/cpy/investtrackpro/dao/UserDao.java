package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;
import com.zufe.cpy.investtrackpro.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);


    /**
     * 构造方法，初始化数据库连接
     */
    public UserDao() {

    }

    /**
     * 插入用户
     *
     * @param user User对象
     * @return 如果插入成功返回true，否则返回false
     */
    public int insert(User user) {
        String sql = "INSERT INTO user (username, password, email, phone, first_name, last_name, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.setString(7, user.getAddress());

            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 删除用户
     *
     * @param user User对象
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean deleteUser(int userId) {

        connection = DataBaseUtil.getConnection();
        String sql = "DELETE FROM user WHERE user_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error deleting user", e);
            return false;
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }
    }

    /**
     * 更新用户
     *
     * @param user User对象
     * @return 如果更新成功返回true，否则返回false
     */
    public boolean updateUser(User user) {
        int userId = user.getUserId();

        String selectSql = "SELECT username, password, email, phone, first_name, last_name, address FROM user WHERE user_id = ?";
        String updateSql = "UPDATE user SET username = ?, password = ?, email = ?, phone = ?, first_name = ?, last_name = ?, address = ?, updated_at = ? WHERE user_id = ?";

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement selectPs = connection.prepareStatement(selectSql);
             PreparedStatement updatePs = connection.prepareStatement(updateSql)) {

            connection.setAutoCommit(false); // 启用事务

            // 获取当前用户数据
            selectPs.setInt(1, userId);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    String currentUsername = rs.getString("username");
                    String currentPassword = rs.getString("password");
                    String currentEmail = rs.getString("email");
                    String currentPhone = rs.getString("phone");
                    String currentFirstName = rs.getString("first_name");
                    String currentLastName = rs.getString("last_name");
                    String currentAddress = rs.getString("address");

                    // 使用传入的User对象更新字段，如果字段为空则保持当前值
                    String newUsername = user.getUsername() != null ? user.getUsername() : currentUsername;
                    String newPassword = user.getPassword() != null ? user.getPassword() : currentPassword;
                    String newEmail = user.getEmail() != null ? user.getEmail() : currentEmail;
                    String newPhone = user.getPhone() != null ? user.getPhone() : currentPhone;
                    String newFirstName = user.getFirstName() != null ? user.getFirstName() : currentFirstName;
                    String newLastName = user.getLastName() != null ? user.getLastName() : currentLastName;
                    String newAddress = user.getAddress() != null ? user.getAddress() : currentAddress;

                    // 更新用户数据
                    updatePs.setString(1, newUsername);
                    updatePs.setString(2, newPassword);
                    updatePs.setString(3, newEmail);
                    updatePs.setString(4, newPhone);
                    updatePs.setString(5, newFirstName);
                    updatePs.setString(6, newLastName);
                    updatePs.setString(7, newAddress);
                    updatePs.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                    updatePs.setInt(9, userId);

                    updatePs.executeUpdate();
                    connection.commit(); // 提交事务
                    return true;
                } else {
                    connection.rollback(); // 回滚事务
                    logger.error("User with ID " + userId + " not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Error updating user", e);
            try {
                if (connection != null) {
                    connection.rollback(); // 回滚事务
                }
            } catch (SQLException rollbackEx) {
                logger.error("Error during rollback", rollbackEx);
            }
        }

        return false;
    }


    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 如果找到返回User对象，否则返回null
     */
    public User findByUsername(String username) {
        connection = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("phone"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("address"), rs.getTimestamp("created_at"), rs.getTimestamp("update_at"), rs.getString("role"));
                    return user;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }
    }

    public User findByEmail(String email) {
        if (!SecurityUtil.isValidEmail(email)) {
            return null;
        }

        connection = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, email);


            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("phone"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("address"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"), rs.getString("role"));
                return user;
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }
    }


    public boolean isExist(int userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.error("Error finding user by user ID", e);
        }
        return false;
    }


    public List<User> findAllUsers() {
        Connection connection = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM user WHERE role='user'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) { // 使用 while 以遍历所有结果
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                String role = rs.getString("role");


                User user = new User(userId, username, password, email, phone, firstName, lastName, address, createdAt, updatedAt, role);
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Error finding all users", e);
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }
        return users; // 总是返回 users 列表，无论是否有结果
    }


    public void update(User user) {
        connection = DataBaseUtil.getConnection();
        String sql = "UPDATE user SET username=?,password=?,email=?,phone=?,first_name=?,last_name=?,address=? WHERE user_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getUserId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insert(Connection conn, User user) {
        String sql = "INSERT INTO user (username, password, email, phone, first_name, last_name, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.setString(7, user.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("插入用户失败", e);
        }
    }

    public void deleteAll() {
        connection = DataBaseUtil.getConnection();
        String sql = "DELETE FROM user where role='user'";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("删除所有用户失败", e);
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, null);
        }
    }


    public User findAdmin() {
        connection = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM user WHERE role='admin'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("phone"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("address"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"), rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error finding admin", e);
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }
        return null;
    }
}
