package com.zufe.cpy.investtrackpro.controller;


import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet("/investment/*")
public class InvestmentController extends HttpServlet {


    private final InvestmentService investmentService = new InvestmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getPathInfo();
        String investmentId = request.getParameter("id");

        if (action == null) {
            action = "/";
        }


        switch (action) {
            case "/":
                showAllInvestments(request, response);
                break;
            case "/search":
                searchInvestments(request, response);
                break;
            case "/details":
                if (investmentId != null) {
                    request.setAttribute("investmentId", investmentId);
                    showInvestmentDetails(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            case "/records":
                showInvestmentRecords(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showAllInvestments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Investment> investments = investmentService.getAllInvestments();
        request.setAttribute("investments", investments);
        request.getRequestDispatcher("/WEB-INF/views/investment.jsp").forward(request, response);
    }

    private void showInvestmentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int investmentId = Integer.parseInt((String) request.getAttribute("investmentId"));

        Investment investment = investmentService.getInvestmentById(investmentId);

        request.setAttribute("investment", investment);
        request.getRequestDispatcher("/WEB-INF/views/investment-details.jsp").forward(request, response);
    }

    private void showInvestmentRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long investmentId = Long.parseLong(request.getParameter("id"));
        List<InvestmentRecord> records = investmentService.getInvestmentRecords(investmentId);
        request.setAttribute("records", records);
        request.getRequestDispatcher("/WEB-INF/views/investment-records.jsp").forward(request, response);
    }

    private void searchInvestments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求参数中获取搜索条件


        Map<String, String> criteria = new HashMap<>();
        criteria.put("category", request.getParameter("category"));
        criteria.put("riskLevel", request.getParameter("riskLevel"));

        //当搜索条件为空时，删除空的搜索条件
        criteria.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().isEmpty());


        List<Investment> investments = investmentService.searchInvestments(criteria);

        // 设置请求属性，传递到 JSP
        request.setAttribute("investments", investments);

        // 获取请求分派器，转发到 JSP 页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/investmentList.jsp");

        // 禁止浏览器或其他缓存此内容，因为内容是动态生成的
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        // 将请求转发到 JSP，JSP 会处理数据并输出 HTML
        dispatcher.include(request, response);
    }


}
