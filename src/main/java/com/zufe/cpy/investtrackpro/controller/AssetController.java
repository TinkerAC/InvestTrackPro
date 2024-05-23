package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.service.AssetService;
import com.zufe.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/asset/*")
public class AssetController extends HttpServlet {

    private AssetService assetService;
    private InvestmentService investmentService;
    @Override
    public void init() throws ServletException {
        assetService = new AssetService();
        investmentService = new InvestmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/buy"://处理买入投资请求
                showInvestmentTradePage(request, response);
                break;
            case "/"://处理查看投资组合请求
                viewAsset(request, response);
                break;
            case "/view":
                viewAsset(request, response);
                break;
            case "/report"://处理生成报告请求
                generateReport(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/buy"://处理买入投资请求
                addInvestmentRecord(request, response);
                break;
            case "/sell"://处理卖出投资请求
                removeInvestment(request, response);
                break;
            case "/view":
                viewAsset(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void viewAsset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的投资组合

        User user = (User) request.getSession().getAttribute("user");
        List<InvestmentRecord> assetList = assetService.getAssetsByUserId(Integer.parseInt(String.valueOf(user.getUserId())));
        request.setAttribute("assetList", assetList);
        request.getRequestDispatcher("/WEB-INF/views/asset.jsp").forward(request, response);

    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedOperationException {
        // 生成投资组合报告
        throw new UnsupportedOperationException("Not supported yet.");
    }


    private void showInvestmentTradePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将url中的investmentId参数传到request中,转发到交易页面

        int investmentId = Integer.parseInt(request.getParameter("investmentId"));
        Investment investment = investmentService.getInvestmentById(investmentId);

        request.setAttribute("investment", investment);
        request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
    }

    private void addInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {
        assetService.addInvestmentRecord(request, response);
    }


    private void removeInvestment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");

    }


}
