package com.zufe.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.service.UserService;
import com.zufe.cpy.investtrackpro.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import static com.zufe.cpy.investtrackpro.util.SecurityUtil.isInvalid;


@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private static class ResponseMessage {
        private String status;
        private String message;

        public ResponseMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private static final String EMAIL_COOKIE = "email";
    private static final String PASSWORD_COOKIE = "password";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo() != null ? request.getPathInfo() : "/";

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
            case "/edit":
                showEditProfilePage(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showEditProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo() != null ? request.getPathInfo() : "/";

        switch (action) {
            case "/register":
                registerUser(request, response);
                break;
            case "/login":
                loginUser(request, response);
                break;
            case "/register_ajax":
                registerUserAjax(request, response);
                break;
            case "/edit":
                editProfile(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        updateUserFromRequest(user, request);
        userService.updateUser(user);
        response.sendRedirect(request.getContextPath() + "/user/profile");
    }

    private void updateUserFromRequest(User user, HttpServletRequest request) {
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(SecurityUtil.hashPassword(request.getParameter("password")));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setAddress(request.getParameter("address"));
        user.setPhone(request.getParameter("phone"));
    }

    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (EMAIL_COOKIE.equals(cookie.getName())) {
                    request.setAttribute("email", cookie.getValue());
                }
                if (PASSWORD_COOKIE.equals(cookie.getName())) {
                    request.setAttribute("password", cookie.getValue());
                }
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void showUserProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (isInvalid(username) || isInvalid(password) || isInvalid(email)) {
            request.setAttribute("error", "所有字段都是必填的哦!");
            showRegistrationForm(request, response);
            return;
        }

        User user = createUserFromRequest(username, password, email);
        int userId = userService.registerUser(user);

        if (userId != -1) {
            response.sendRedirect(request.getContextPath() + "/user/login");
        } else {
            request.setAttribute("error", "该邮箱已被注册.");
            showRegistrationForm(request, response);
        }
    }


    private User createUserFromRequest(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecurityUtil.hashPassword(password));
        user.setRole("user");
        user.setEmail(email);
        return user;
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 检查邮箱或密码是否为空
        if (isInvalid(email) || isInvalid(password)) {
            request.setAttribute("error", "邮箱或密码不能为空!");
            showLoginForm(request, response);
            return;
        }

        // 检查用户是否存在
        boolean isUserExist = userService.isUserExist(email);
        if (!isUserExist) {
            request.setAttribute("error", "您还没有注册，请先注册！");
            showLoginForm(request, response);
            return;
        }

        // 尝试登录用户
        User user = userService.loginUser(email, password);

        if (user != null) { // 如果用户存在，登录成功
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("admin".equals(user.getRole())) { // 如果是管理员，跳转到管理员页面
                request.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(request, response);
            } else { // 如果是普通用户，跳转到用户页面
                String originalUrl = (String) request.getSession().getAttribute("originalUrl");
                if (originalUrl != null) { // 如果有原始请求，跳转到原始请求
                    response.sendRedirect(originalUrl);
                } else { // 如果没有原始请求，跳转到用户个人资料页面
                    response.sendRedirect(request.getContextPath() + "/user/profile");
                }
            }
        } else { // 如果用户不存在，登录失败
            request.setAttribute("error", "邮箱或密码错误!");
            showLoginForm(request, response);
        }
    }


    private void loginUserDev(HttpServletRequest request, HttpServletResponse response, String email, String password) throws ServletException, IOException {
        if (isInvalid(email) || isInvalid(password)) {
            request.setAttribute("error", "邮箱或密码不能为空!");
            showLoginForm(request, response);
            return;
        }

        User user = userService.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/user/profile");
        } else {
            request.setAttribute("error", "邮箱或密码错误!");
            showLoginForm(request, response);
        }
    }


    private void registerUserAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        if (isInvalid(username) || isInvalid(password) || isInvalid(email)) {
            ResponseMessage errorMessage = new ResponseMessage("error", "所有字段都是必填的哦!");
            out.print(gson.toJson(errorMessage));
            out.flush();
            return;
        }

        User user = createUserFromRequest(username, password, email);
        int userId = userService.registerUser(user);

        if (userId != -1) {
            ResponseMessage successMessage = new ResponseMessage("success", "用户已成功注册");
            out.print(gson.toJson(successMessage));
        } else {
            ResponseMessage errorMessage = new ResponseMessage("error", "该邮箱已被注册.");
            out.print(gson.toJson(errorMessage));
        }

        out.flush();
    }
}



