package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.investtrackpro.service.PortfolioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/portfolio/*")
public class PortfolioController extends HttpServlet {

    private PortfolioService portfolioService;

    @Override
    public void init() throws ServletException {
        portfolioService = new PortfolioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/view":
                viewPortfolio(request, response);
                break;
            case "/report":
                generateReport(request, response);
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
            case "/add":
                addInvestment(request, response);
                break;
            case "/remove":
                removeInvestment(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void viewPortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的投资组合
        String userId = (String) request.getSession().getAttribute("userId");
        List<InvestmentRecord> portfolioList = portfolioService.getPortfolioByUserId(Long.parseLong(userId));
        request.setAttribute("portfolioList", portfolioList);
        request.getRequestDispatcher("/WEB-INF/views/portfolio.jsp").forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成投资组合报告
        String userId = (String) request.getSession().getAttribute("userId");
        Portfolio report = portfolioService.generateReport(Long.parseLong(userId));
        request.setAttribute("report", report);
        request.getRequestDispatcher("/WEB-INF/views/report.jsp").forward(request, response);
    }

    private void addInvestment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 添加投资项目到投资组合
        String userId = (String) request.getSession().getAttribute("userId");
        String investmentId = request.getParameter("investmentId");
        portfolioService.addInvestmentToPortfolio(Long.parseLong(userId), Long.parseLong(investmentId));
        response.sendRedirect(request.getContextPath() + "/portfolio/view");
    }

    private void removeInvestment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从投资组合中移除投资项目
        String userId = (String) request.getSession().getAttribute("userId");
        String investmentId = request.getParameter("investmentId");
        portfolioService.removeInvestmentFromPortfolio(Long.parseLong(userId), Long.parseLong(investmentId));
        response.sendRedirect(request.getContextPath() + "/portfolio/view");
    }
}
