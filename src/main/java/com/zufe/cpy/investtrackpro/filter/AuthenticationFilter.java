package com.zufe.cpy.investtrackpro.filter;

import com.zufe.cpy.investtrackpro.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/user/profile", "/user/logout", "/asset/*", "/admin/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // 如果用户已登录
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String role = user.getRole();
            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

            // 检查路径是否是 /admin/*
            if (path.startsWith("/admin") && !role.equals("admin")) {
                // 如果不是 admin 用户，重定向到权限不足页面
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/noAccess");
            } else {
                // 如果是合法用户，继续请求
                chain.doFilter(request, response);
            }
        } else {
            // 如果用户未登录，存储原始请求URL
            String originalUrl = httpRequest.getRequestURI();
            if (httpRequest.getQueryString() != null) {
                originalUrl += "?" + httpRequest.getQueryString();
            }
            session = httpRequest.getSession(true);
            session.setAttribute("originalUrl", originalUrl);

            // 重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login");
        }
    }

    @Override
    public void destroy() {
        // Filter destruction code if needed
    }
}

