package com.rjgc.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.rjgc.cpy.investtrackpro.model.*;
import com.rjgc.cpy.investtrackpro.service.AssetService;
import com.rjgc.cpy.investtrackpro.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/asset")
public class AssetController {


    @Autowired
    private AssetService assetService;

    @Autowired
    private InvestmentService investmentService;

    @GetMapping("/buy")
    public String showBuyPage(HttpServletRequest request, Model model) {
        return showInvestmentTradePage(request, model);
    }

    @GetMapping("/sell")
    public String showSellPage(HttpServletRequest request, Model model) {
        return showInvestmentTradePage(request, model);
    }

    @GetMapping({"", "/", "/view"})
    public String viewAsset(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        assetService.updateAsset(user.getUserId());
        List<Asset> assets = assetService.getAssetsByUserId(user.getUserId());

        // 生成投资id到投资对象的映射
        Map<Integer, Investment> investmentMap = new java.util.HashMap<>();
        for (Asset asset : assets) {
            Investment investment = investmentService.getInvestmentById(asset.getInvestmentId());
            investmentMap.put(asset.getInvestmentId(), investment);
        }

        model.addAttribute("assets", assets);
        model.addAttribute("investmentMap", investmentMap);

        return "assets"; // 对应 /WEB-INF/views/assets.jsp
    }

    @GetMapping("/report")
    public String showReportPage(HttpServletRequest request, Model model) {
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
        BigDecimal[] dataForPieChart = new BigDecimal[5];
        Arrays.fill(dataForPieChart, BigDecimal.ZERO);

        for (Asset asset : assets) {
            int riskLevel = investmentService.getInvestmentById(asset.getInvestmentId()).getRiskLevel() - 1;
            dataForPieChart[riskLevel] = dataForPieChart[riskLevel].add(asset.getAmount().multiply(investmentService.getInvestmentById(asset.getInvestmentId()).getCurrentValue()));
        }

        // 生成收益率直方图数据
        List<BigDecimal> ROIs = new ArrayList<>();
        for (User user1 : assetService.getUserList()) {
            ROIs.add(assetService.getReturnOnInvestment(user1.getUserId()));
        }
        BigDecimal userROI = assetService.getReturnOnInvestment(userId);

        // 生成用户资产成分数据
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

        model.addAttribute("assets", assets);
        model.addAttribute("investments", investments);
        model.addAttribute("userTotalAssetValue", userTotalAssetValue);
        model.addAttribute("investmentRecords", investmentRecords);
        model.addAttribute("investmentDailyChanges", investmentDailyChanges);
        model.addAttribute("dataForPieChart", dataForPieChartJson);
        model.addAttribute("ROIs", profitsJson);
        model.addAttribute("userROI", userROI);
        model.addAttribute("userRank", userRank); // 用户打败了userRank%的用户
        model.addAttribute("dataForComponentPieChart", dataForComponentPieChartJson);

        return "report"; // 对应 /WEB-INF/views/report.jsp
    }

    @PostMapping("/buy")
    public String addBoughtInvestmentRecord(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));

        boolean isAssetExist = assetService.isAssetExist(user.getUserId(), investmentId);

        // 如果用户没有买过，就添加初始记录
        int assetId;
        if (!isAssetExist) {
            Asset asset = new Asset();
            asset.setUserId(user.getUserId());
            asset.setInvestmentId(investmentId);
            asset.setAmount(BigDecimal.ZERO);
            assetId = assetService.addAsset(asset);
        } else {
            assetId = assetService.getAssetId(user.getUserId(), investmentId);
        }

        // 调用服务层添加投资记录
        if (assetId == -1) {
            model.addAttribute("message", "买入失败");
            return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
        }

        boolean isSuccess = assetService.addBoughtInvestmentRecord(user.getUserId(), investmentId, assetId, new BigDecimal(request.getParameter("amount")));

        if (!isSuccess) {
            model.addAttribute("message", "买入失败");
        } else {
            model.addAttribute("message", "买入成功");
        }

        assetService.updateAsset(user.getUserId());
        return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
    }

    @PostMapping("/sell")
    public String addSoldInvestmentRecord(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "用户未登录");
            return "login"; // 对应 /WEB-INF/views/login.jsp
        }

        int investmentId;
        BigDecimal amount;
        try {
            investmentId = Integer.parseInt(request.getParameter("investmentId"));
            amount = new BigDecimal(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            model.addAttribute("message", "无效的投资ID或卖出数量");
            return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
        }

        int assetId = assetService.getAssetId(user.getUserId(), investmentId);

        boolean isAssetExist = assetService.isAssetExist(user.getUserId(), investmentId);
        if (!isAssetExist) {
            model.addAttribute("message", "卖出失败，没有持有该资产");
            return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
        }

        BigDecimal holdingAmount = assetService.getAssetAmount(user.getUserId(), investmentId);

        if (holdingAmount.compareTo(amount) < 0) {
            model.addAttribute("message", "卖出失败，持有量不足");
            return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
        }

        boolean isSuccess = assetService.addSoldInvestmentRecord(user.getUserId(), investmentId, assetId, amount);

        if (!isSuccess) {
            model.addAttribute("message", "卖出失败");
        } else {
            model.addAttribute("message", "卖出成功");
        }
        assetService.updateAsset(user.getUserId());

        return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
    }

    private String showInvestmentTradePage(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));
        Investment investment = investmentService.getInvestmentById(investmentId);
        Asset asset = assetService.getAsset(user.getUserId(), investmentId);
        if (asset != null) {
            model.addAttribute("asset", asset);
        }

        model.addAttribute("investment", investment);
        return "tradePage"; // 对应 /WEB-INF/views/tradePage.jsp
    }
}
