package com.rjgc.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.rjgc.cpy.investtrackpro.model.*;
import com.rjgc.cpy.investtrackpro.service.AssetService;
import com.rjgc.cpy.investtrackpro.model.*;
import com.rjgc.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@WebServlet("/asset/*")
public class AssetController extends HttpServlet {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetController.class);
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
            case "/sell"://处理卖出投资请求
                showInvestmentTradePage(request, response);
                break;
            case "/"://处理查看投资组合请求
                viewAsset(request, response);
                break;
            case "/view":
                viewAsset(request, response);
                break;
            case "/report"://处理生成报告请求
                showReportPage(request, response);
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
                addSoldInvestmentRecord(request, response);
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
        assetService.updateAsset(user.getUserId());
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

    private void showReportPage(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int userId = user.getUserId();

        // 更新收益情况
        assetService.updateAsset(userId);

        // 获取用户的资产、投资记录和投资信息
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        List<InvestmentRecord> investmentRecords = assetService.getInvestmentRecordsByUserId(userId);
        List<InvestmentDailyChange> investmentDailyChanges = new ArrayList<>();
        List<Investment> investments = new ArrayList<>();

        for (Asset asset : assets) {
            int investmentId = asset.getInvestmentId();
            investmentDailyChanges.add(investmentService.getLatestInvestmentDailyChanges(investmentId));
            investments.add(investmentService.getInvestmentById(investmentId));
        }
        BigDecimal userTotalAssetValue = assetService.getTotalAssetValue(userId);

        // 生成饼图数据
        String[] labelsForPieChart = {"低风险", "中低风险", "中风险", "中高风险", "高风险"};
        BigDecimal[] dataForPieChart = new BigDecimal[5];
        Arrays.fill(dataForPieChart, BigDecimal.ZERO);

        for (Asset asset : assets) {
            int riskLevel = investmentService.getInvestmentById(asset.getInvestmentId()).getRiskLevel() - 1;
            dataForPieChart[riskLevel] = dataForPieChart[riskLevel].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));

        }

        for (BigDecimal data : dataForPieChart) {
            System.out.println(data);
        }

        //生成收益率直方图数据
        List<BigDecimal> ROIs = new ArrayList<>();
        for (User user1 : assetService.getUserList()) {
            ROIs.add(assetService.getReturnOnInvestment(user1.getUserId()));
        }
        BigDecimal userROI = assetService.getReturnOnInvestment(userId);

        //生成用户资产成分数据
        BigDecimal[] dataForComponentPieChart = new BigDecimal[5];
        Arrays.fill(dataForComponentPieChart, BigDecimal.ZERO);
        for (Asset asset : assets) {
            String Category = investmentService.getInvestmentById(asset.getInvestmentId()).getCategory();
            // ["股票", "债券", "基金","房地产","大宗商品"],
            switch (Category) {
                case "股票":
                    dataForComponentPieChart[0] = dataForComponentPieChart[0].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
                    break;
                case "债券":
                    dataForComponentPieChart[1] = dataForComponentPieChart[1].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
                    break;
                case "基金":
                    dataForComponentPieChart[2] = dataForComponentPieChart[2].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
                    break;
                case "房地产":
                    dataForComponentPieChart[3] = dataForComponentPieChart[3].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
                    break;
                case "大宗商品":
                    dataForComponentPieChart[4] = dataForComponentPieChart[4].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
                    break;
            }

        }

        // 计算用户ROI的排名百分比
        int rank = 0;
        for (BigDecimal roi : ROIs) {
            if (roi.compareTo(userROI) < 0) {
                rank++;
            } else {
                break;
            }
        }
        double userRank = (double) rank / ROIs.size() * 100;

        // 传递数据到前端
        String dataForPieChartJson = new Gson().toJson(dataForPieChart);
        String profitsJson = new Gson().toJson(ROIs);
        String dataForComponentPieChartJson = new Gson().toJson(dataForComponentPieChart);

        request.setAttribute("assets", assets);
        request.setAttribute("investments", investments);
        request.setAttribute("userTotalAssetValue", userTotalAssetValue);
        request.setAttribute("investmentRecords", investmentRecords);
        request.setAttribute("investmentDailyChanges", investmentDailyChanges);
        request.setAttribute("dataForPieChart", dataForPieChartJson);
        request.setAttribute("ROIs", profitsJson);
        request.setAttribute("userROI", userROI);
        request.setAttribute("userRank", userRank);//用户打败了userRank%的用户
        request.setAttribute("dataForComponentPieChart", dataForComponentPieChartJson);

        try {
            request.getRequestDispatcher("/WEB-INF/views/report.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in showReportPage", e);
        }
    }


    private void showInvestmentTradePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将url中的investmentId参数传到request中,转发到交易页面
        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));
        Investment investment = investmentService.getInvestmentById(investmentId);
        Asset asset = assetService.getAsset(user.getUserId(), investmentId);
        if (asset != null) {
            request.setAttribute("asset", asset);
        }

        request.setAttribute("investment", investment);
        request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
    }

    private void addBoughtInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));


        boolean isAssetExist = assetService.isAssetExist(user.getUserId(), investmentId);

        //如果用户没有买过，就添加初始记录
        int assetId = -1;
        if (!isAssetExist) {
            Asset asset = new Asset();
            asset.setUserId(user.getUserId());
            asset.setInvestmentId(investmentId);
            asset.setAmount(BigDecimal.ZERO);
            assetId = assetService.addAsset(asset);
        } else {
            assetId = assetService.getAssetId(user.getUserId(), investmentId);
        }


        //调用服务层添加投资记录
        if (assetId == -1) {
            request.setAttribute("message", "买入失败");
            try {
                request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);

            } catch (Exception e) {
                logger.error("Error in addBoughtInvestmentRecord", e);
            }
            return;
        }

        boolean isSuccess = assetService.addBoughtInvestmentRecord(user.getUserId(), investmentId, assetId, new BigDecimal(request.getParameter("amount")));


        if (!isSuccess) {
            request.setAttribute("message", "买入失败");
        } else {
            request.setAttribute("message", "买入成功");
        }

        assetService.updateAsset(user.getUserId());
        try {
            request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in addBoughtInvestmentRecord", e);
        }


    }

    private void addSoldInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "用户未登录");
            try {
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } catch (Exception e) {
                logger.error("Error in addSoldInvestmentRecord - User not logged in", e);
            }
            return;
        }

        int investmentId;
        BigDecimal amount;
        try {
            investmentId = Integer.parseInt(request.getParameter("investmentId"));
            amount = new BigDecimal(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "无效的投资ID或卖出数量");
            try {
                request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
            } catch (Exception ex) {
                logger.error("Error in addSoldInvestmentRecord - Invalid investmentId or amount", ex);
            }
            return;
        }

        int assetId = assetService.getAssetId(user.getUserId(), investmentId);

        boolean isAssetExist = assetService.isAssetExist(user.getUserId(), investmentId);
        if (!isAssetExist) {
            request.setAttribute("message", "卖出失败，没有持有该资产");
            try {
                request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
            } catch (Exception e) {
                logger.error("Error in addSoldInvestmentRecord - Asset does not exist", e);
            }
            return;
        }

        BigDecimal holdingAmount = assetService.getAssetAmount(user.getUserId(), investmentId);

        if (holdingAmount.compareTo(amount) < 0) {
            request.setAttribute("message", "卖出失败，持有量不足");
            try {
                request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
            } catch (Exception e) {
                logger.error("Error in addSoldInvestmentRecord - Insufficient holding amount", e);
            }
            return;
        }

        boolean isSuccess = assetService.addSoldInvestmentRecord(user.getUserId(), investmentId, assetId, amount);

        if (!isSuccess) {
            request.setAttribute("message", "卖出失败");
        } else {
            request.setAttribute("message", "卖出成功");
        }
        assetService.updateAsset(user.getUserId());

        try {
            request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in addSoldInvestmentRecord", e);
        }
    }


    private void removeInvestment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");

    }


}
