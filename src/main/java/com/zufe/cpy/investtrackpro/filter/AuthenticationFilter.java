package com.zufe.cpy.investtrackpro.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/portfolio/*", "/user/profile", "/user/logout"})
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

        //如果用户已登录，则继续请求
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            //如果用户未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login");
        }
    }

    @Override
    public void destroy() {
        // Filter destruction code if needed
    }
}
