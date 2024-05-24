package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.service.UserService;
import com.zufe.cpy.investtrackpro.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/user/*")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/register":
                showRegistrationForm(request, response);
                break;
            case "/login":
                showLoginForm(request, response);
                break;
            case "/profile":
                showUserProfile(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "/login_dev":
                loginUserDev(request, response, "2058666094@qq.com", "123456");
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/register":
                registerUser(request, response);
                break;
            case "/login":
                loginUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void showUserProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/user/login");
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "所有字段都是必填的哦!");
            showRegistrationForm(request, response);
            return;
        }

        //新建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecurityUtil.hashPassword(password));
        user.setRole("user");
        user.setEmail(email);

        //调用服务层方法注册用户
        int userId = userService.registerUser(user);

        //如果用户不存在，注册成功,重定向到登录页面
        if (userId != -1) {
            response.sendRedirect(request.getContextPath() + "/user/login");
        } else {
            request.setAttribute("error", "该邮箱已被注册.");
            showRegistrationForm(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "邮箱或密码不能为空!");
            showLoginForm(request, response);
            return;
        }

        User user = userService.loginUser(email, password);


        if (user != null) {
            String role = user.getRole();
            if (role.equals("admin")) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/user/profile");
            }
        } else {
            request.setAttribute("error", "邮箱或密码错误!");
            showLoginForm(request, response);
        }
    }

    private void loginUserDev(HttpServletRequest request, HttpServletResponse response, String email, String password) throws ServletException, IOException {

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "邮箱或密码不能为空!");
            showLoginForm(request, response);
            return;
        }

        User user = userService.loginUser(email, password);

        //如果用户存在，登录成功,重定向到来源页面
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/user/profile");
        } else {
            request.setAttribute("error", "邮箱或密码错误!");
            showLoginForm(request, response);
        }
    }
}
