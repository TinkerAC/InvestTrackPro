package com.rjgc.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.rjgc.cpy.investtrackpro.model.User;
import com.rjgc.cpy.investtrackpro.service.UserService;
import com.rjgc.cpy.investtrackpro.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import static com.rjgc.cpy.investtrackpro.util.SecurityUtil.isInvalid;

@Controller
@RequestMapping("/user")
public class UserController {

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

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // 返回视图名称
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (EMAIL_COOKIE.equals(cookie.getName())) {
                    model.addAttribute("email", cookie.getValue());
                }
                if (PASSWORD_COOKIE.equals(cookie.getName())) {
                    model.addAttribute("password", cookie.getValue());
                }
            }
        }
        return "login"; // 返回视图名称
    }

    @GetMapping("/profile")
    public String showUserProfile() {
        return "profile"; // 返回视图名称
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/login_dev")
    public String loginUserDev(HttpServletRequest request, Model model) {
        return loginUser(request, model, "2058666094@qq.com", "123456");
    }

    @GetMapping("/edit")
    public String showEditProfilePage(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "editProfile"; // 返回视图名称
    }

    @PostMapping("/register")
    public String registerUser(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (isInvalid(username) || isInvalid(password) || isInvalid(email)) {
            model.addAttribute("error", "所有字段都是必填的哦!");
            return "register";
        }

        User user = createUserFromRequest(username, password, email);
        int userId = userService.registerUser(user);

        if (userId != -1) {
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "该邮箱已被注册.");
            return "register";
        }
    }

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, Model model) {
        return loginUser(request, model, request.getParameter("email"), request.getParameter("password"));
    }

    private String loginUser(HttpServletRequest request, Model model, String email, String password) {
        if (isInvalid(email) || isInvalid(password)) {
            model.addAttribute("error", "邮箱或密码不能为空!");
            return "login";
        }

        boolean isUserExist = userService.isUserExist(email);

        if (!isUserExist) {
            model.addAttribute("error", "您还没有注册，请先注册！");
            return "login";
        }

        User user = userService.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("admin".equals(user.getRole())) {
                return "redirect:/admin";
            } else {
                String originalUrl = (String) session.getAttribute("originalUrl");
                if (originalUrl != null) {
                    return "redirect:" + originalUrl;
                } else {
                    return "redirect:/user/profile";
                }
            }
        } else {
            model.addAttribute("error", "邮箱或密码错误!");
            return "login";
        }
    }

    @PostMapping("/edit")
    public String editProfile(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        updateUserFromRequest(user, request);
        userService.updateUser(user);
        return "redirect:/user/profile";
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

    @PostMapping("/register_ajax")
    public ResponseEntity<ResponseMessage> registerUserAjax(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (isInvalid(username) || isInvalid(password) || isInvalid(email)) {
            return ResponseEntity.badRequest().body(new ResponseMessage("error", "所有字段都是必填的哦!"));
        }

        User user = createUserFromRequest(username, password, email);
        int userId = userService.registerUser(user);

        if (userId != -1) {
            return ResponseEntity.ok(new ResponseMessage("success", "用户已成功注册"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("error", "该邮箱已被注册."));
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
}
