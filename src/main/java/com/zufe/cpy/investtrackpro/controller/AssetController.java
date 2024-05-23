package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.Asset;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                addBoughtInvestmentRecord(request, response);
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


    //显示用户持有的资产
    private void viewAsset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        List<Asset> assets = assetService.getAssetsByUserId(user.getUserId());

        //生成投资id到投资对象的映射
        Map<Integer, Investment> investmentMap = new java.util.HashMap<>(Map.of());
        for (Asset asset : assets) {
            Investment investment = investmentService.getInvestmentById(asset.getInvestmentId());
            investmentMap.put(asset.getInvestmentId(), investment);
        }


        request.setAttribute("assets", assets);
        request.setAttribute("investmentMap", investmentMap);

        request.getRequestDispatcher("/WEB-INF/views/assets.jsp").forward(request, response);

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

    private void addBoughtInvestmentRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));


        boolean isAssetExist = assetService.isAssetExist(user.getUserId(), investmentId);

        //如果用户没有买过，就添加初始记录
        int assetId = -1;
        if (!isAssetExist) {
            Asset asset = new Asset();
            asset.setUserId(user.getUserId());
            asset.setInvestmentId(investmentId);
            asset.setAmount(0.0);
            assetId = assetService.addAsset(asset);
        } else {
            assetId = assetService.getAssetId(user.getUserId(), investmentId);
        }


        //调用服务层添加投资记录
        if (assetId == -1) {
            request.setAttribute("message", "买入失败");
            request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
            return;
        }
        boolean isSuccess = assetService.addInvestmentRecord(request, "买入", assetId);


        if (!isSuccess) {
            request.setAttribute("message", "买入失败");
        } else {
            request.setAttribute("message", "买入成功");
            assetService.updateAsset(user.getUserId());
        }
        request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);

    }


    private void removeInvestment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");

    }


}
