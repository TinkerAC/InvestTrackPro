package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.service.AdminService;
import com.zufe.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService = new AdminService();
    private final InvestmentService investmentService = new InvestmentService();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();

        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/":
                showAdminIndexPage(request, response);
                break;
            case "/user"://查看用户列表
                showUserList(request, response);
                break;
            case "/investment"://查看投资列表
                showInvestmentList(request, response);
                break;
            case "/investmentRecord"://查看交易记录列表
                showInvestmentRecordList(request, response);
                break;
            case "/madeInHaven"://模拟时间过一天
                madeInHaven(request, response);
                break;
            case "/resetSystem"://重置系统
                resetSystem(request, response);
                break;
            case "/logout":
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/");
                break;
            default:
                showNotFoundPage(request, response);
                break;

        }


    }

    private void resetSystem(HttpServletRequest request, HttpServletResponse response) {
        adminService.resetSystem();
    }

    private void madeInHaven(HttpServletRequest request, HttpServletResponse response) {
        adminService.madeInHaven();
        request.setAttribute("message", "模拟时间流逝成功!");
        showAdminIndexPage(request, response);
    }

    private void showInvestmentRecordList(HttpServletRequest request, HttpServletResponse response) {
        List<InvestmentRecord> investmentRecords = investmentService.getInvestmentRecordList();
        request.setAttribute("investmentRecords", investmentRecords);

        try {
            request.getRequestDispatcher("/WEB-INF/views/admin/investmentRecordList.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showInvestmentRecordList", e);
        }
    }

    private void showInvestmentList(HttpServletRequest request, HttpServletResponse response) {
        List<Investment> investments = investmentService.getInvestmentList();
        request.setAttribute("investments", investments);
        try {
            request.getRequestDispatcher("/WEB-INF/views/admin/investmentList.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showInvestmentList", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }


    private void showUserList(HttpServletRequest request, HttpServletResponse response) {

        List<User> users = adminService.getUserList();
        request.setAttribute("users", users);
        try {
            request.getRequestDispatcher("/WEB-INF/views/admin/userList.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showUserList", e);
        }
    }

    private void showNotFoundPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showNotFoundPage", e);
        }
    }

    private void showAdminIndexPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showAdminIndexPage", e);
        }
    }
}
