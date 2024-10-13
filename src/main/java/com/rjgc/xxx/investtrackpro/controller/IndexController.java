package com.rjgc.xxx.investtrackpro.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class IndexController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/noAccess":
                showNoAccessPage(request, response);
                break;
            case "/notFound":
                showNotFoundPage(request, response);
                break;
            case "/":
                showIndexPage(request, response);
                break;
            case "/logout":
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath());
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void showIndexPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showIndexPage", e);
        }
    }

    private void showNotFoundPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showNotFoundPage", e);
        }
    }

    private void showNoAccessPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/views/noAccess.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error in showNoAccessPage", e);
        }

    }

}
