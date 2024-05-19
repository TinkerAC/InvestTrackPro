package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import com.zufe.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/investment/*")
public class InvestmentController extends HttpServlet {


    private final InvestmentService investmentService = new InvestmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getPathInfo();
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
                showInvestmentDetails(request, response);
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
        request.getRequestDispatcher("/WEB-INF/views/investment-list.jsp").forward(request, response);
    }

    private void showInvestmentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long investmentId = Long.parseLong(request.getParameter("id"));
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
        Map<String, String> criteria = Map.of(
                "category", request.getParameter("category"),
                "riskLevel", request.getParameter("riskLevel")
        );
        List<Investment> investments = investmentService.searchInvestments(criteria);
        request.setAttribute("investments", investments);
        request.getRequestDispatcher("/WEB-INF/views/investment-list.jsp").forward(request, response);
    }


}
