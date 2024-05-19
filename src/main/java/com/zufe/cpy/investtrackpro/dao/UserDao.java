package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.util.DBUtils;
import com.zufe.cpy.investtrackpro.util.SecurityUtil;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Connection connection;

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
        try (Connection connection = DBUtils.getConnection();
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
    public boolean deleteUser(User user) {

        connection = DBUtils.getConnection();
        String sql = "DELETE FROM user WHERE username = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeJDBC(connection, ps, rs);
        }
    }

    /**
     * 更新用户
     *
     * @param user User对象
     * @return 如果更新成功返回true，否则返回false
     */
    public boolean updateUser(User user) {


        int userId = findByEmail(user.getEmail()).getUserId();
        //如果找不到用户，返回false
        if (userId == -1) {
            return false;
        }
        connection = DBUtils.getConnection();
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
            ps.setInt(8, userId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeJDBC(connection, ps, rs);
        }
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 如果找到返回User对象，否则返回null
     */
    public User findByUsername(String username) {
        connection = DBUtils.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeJDBC(connection, ps, rs);
        }
    }

    public User findByEmail(String email) {
        if (!SecurityUtil.isValidEmail(email)) {
            return null;
        }
        connection = DBUtils.getConnection();
        String sql = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(sql);

            ps.setString(1, email);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeJDBC(connection, ps, rs);
        }
    }

    public static void test() {
        UserDao userDao = new UserDao();
        System.out.println("正在插入User...");
        userDao.insert(new User("test_username", "test_password", "test_email", null, null, null, null));

        System.out.println("正在查找User...");
        User user_0 = userDao.findByUsername("test_username");
        System.out.println("查找到的User:");
        System.out.println(user_0);

        System.out.println("修改前的User:");
        System.out.println(user_0);
        userDao.updateUser(new User("test_username", "updated_password", "updated_email", "123456", null, null, null));
        System.out.println("修改后的User:");
        System.out.println(userDao.findByUsername("test_username"));

        System.out.println("正在删除User...");

        userDao.deleteUser(userDao.findByUsername("test_username"));

    }


}
