package com.zufe.cpy.investtrackpro.util;

import java.sql.*;

public class DBUtils {
    private static final String userName = "root";
    private static final String password = "mysql";
    private static final String url = "jdbc:mysql://localhost:3306/invest_track_pro";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeJDBC(Connection conn, Statement st, ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (st != null)
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (conn != null)
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from user");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println("用户名\t密码\t邮箱\t名\t姓\t电话\t地址");
            System.out.println(rs.getString("username") + "\t" + rs.getString("password") + "\t" + rs.getString("email") + "\t" + rs.getString("first_name") + "\t" + rs.getString("last_name") + "\t" + rs.getString("phone") + "\t" + rs.getString("address"));
        }

    }
}