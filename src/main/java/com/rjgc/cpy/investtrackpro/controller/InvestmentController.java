package com.rjgc.cpy.investtrackpro.controller;


import com.google.gson.Gson;
import com.rjgc.cpy.investtrackpro.model.Investment;
import com.rjgc.cpy.investtrackpro.model.InvestmentDailyChange;
import com.rjgc.cpy.investtrackpro.model.InvestmentRecord;
import com.rjgc.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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

        // 从investments生成investmentIdList
        List<Integer> investmentIdList = new ArrayList<>();
        for (Investment investment : investments) {
            investmentIdList.add(investment.getInvestmentId());
        }
        List<InvestmentDailyChange> investmentDailyChanges = investmentService.getInvestmentDailyChanges(investmentIdList);
        Map<Integer, InvestmentDailyChange> investmentDailyChangeMap = new HashMap<>();

        // 生成从investmentId到investmentDailyChange的映射
        if (investmentDailyChanges != null && !investmentDailyChanges.isEmpty()) {
            for (InvestmentDailyChange investmentDailyChange : investmentDailyChanges) {
                if (investmentDailyChange != null) { // 添加空值检查
                    investmentDailyChangeMap.put(investmentDailyChange.getInvestmentId(), investmentDailyChange);
                }
            }
        }

        request.setAttribute("investmentDailyChangeMap", investmentDailyChangeMap);
        request.getRequestDispatcher("/WEB-INF/views/investment.jsp").forward(request, response);
    }


    private void showInvestmentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int investmentId = Integer.parseInt((String) request.getAttribute("investmentId"));

        Investment investment = investmentService.getInvestmentById(investmentId);

        List<InvestmentDailyChange> dailyChanges = investmentService.getInvestmentDailyChanges(investmentId);

        List<Timestamp> dates = new ArrayList<>();
        List<BigDecimal> closingValues = new ArrayList<>();
        for (InvestmentDailyChange dailyChange : dailyChanges) {
            dates.add(dailyChange.getCreatedAt());
            closingValues.add(dailyChange.getClosingValue());
        }

        request.setAttribute("dates", new Gson().toJson(dates));
        request.setAttribute("values", new Gson().toJson(closingValues));

        request.setAttribute("investment", investment);
        request.getRequestDispatcher("/WEB-INF/views/investment-details.jsp").forward(request, response);
    }

    private void showInvestmentRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int investmentId = Integer.parseInt(request.getParameter("id"));
        List<InvestmentRecord> records = investmentService.getInvestmentRecordList();
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
