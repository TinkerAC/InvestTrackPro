package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.UserDao;
import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.util.SecurityUtil;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    /**
     * 注册用户
     *
     * @param user User对象，包含用户名、密码和电子邮件
     * @return 如果用户注册成功返回true，否则返回false
     */
    public int registerUser(User user) {
        // 检查邮箱是否已经被注册
        if (userDao.findByEmail(user.getEmail()) != null) {
            return -1;
        }

        // 保存新用户到数据库
        return userDao.insert(user);
    }

    /**
     * 用户登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 如果登录成功返回User对象，否则返回null
     */
    public User loginUser(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && SecurityUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void updateUser(User user) {
        userDao.update(user);
    }


    // 其他用户相关的业务逻辑方法...
}
