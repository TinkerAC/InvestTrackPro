package com.zufe.cpy.investtrackpro.controller;

import com.zufe.cpy.investtrackpro.model.Asset;
import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.model.User;
import com.zufe.cpy.investtrackpro.service.AdminService;
import com.zufe.cpy.investtrackpro.service.AssetService;
import com.zufe.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService = new AdminService();
    private final InvestmentService investmentService = new InvestmentService();
    private final AssetService assetService = new AssetService();

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
            case "/randomUser":
                addRandomUser(request, response);
                break;
            case "/randomBuy":
                addRandomBoughtInvestmentRecord(request, response);
                break;
            case "/randomSell":
                addRandomSellInvestmentRecord(request, response);
                break;
            default:
                showNotFoundPage(request, response);
                break;

        }


    }

    private void addRandomBoughtInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {

        List<User> users = adminService.getUserList();
        List<Investment> investments = investmentService.getInvestmentList();

        for (User user : users) {
            // 每个用户随机买入5次
            for (int i = 0; i < 5; i++) {
                if (investments.isEmpty()) {
                    continue; // 避免投资列表为空时导致异常
                }
                int investmentIndex = (int) (Math.random() * investments.size());
                Investment randomInvestment = investments.get(investmentIndex);
                boolean isAssetExist = assetService.isAssetExist(user.getUserId(), randomInvestment.getInvestmentId());

                // 如果用户没有买过，就添加初始记录
                int assetId = -1;
                if (!isAssetExist) {
                    Asset asset = new Asset();
                    asset.setUserId(user.getUserId());
                    asset.setInvestmentId(randomInvestment.getInvestmentId());
                    asset.setAmount(0.0);
                    assetId = assetService.addAsset(asset);
                } else {
                    assetId = assetService.getAssetId(user.getUserId(), randomInvestment.getInvestmentId());
                }

                // 调用服务层添加投资记录
                if (assetId == -1) {
                    continue;
                }

                // 随机买入0~15份，保留两位小数
                double randomAmount = Math.random() * 15;
                double roundedAmount = Double.parseDouble(String.format("%.2f", randomAmount));

                boolean isSuccess = assetService.addBoughtInvestmentRecord(user.getUserId(), randomInvestment.getInvestmentId(), assetId, roundedAmount);
            }
        }

        // 更新所有用户的资产
        for (User user : users) {
            assetService.updateAsset(user.getUserId());
        }

        showAdminIndexPage(request, response);
    }


    private void addRandomSellInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = adminService.getUserList();

        for (User user : users) {
            // 获取用户持有的资产列表
            List<Asset> assets = assetService.getAssetsByUserId(user.getUserId());
            // 随机选取卖出次数，范围为0~持有资产数
            int count = (int) (Math.random() * assets.size());
            // 打乱资产列表，确保每次选取的是不重复的资产
            Collections.shuffle(assets);

            for (int i = 0; i < count; i++) {
                Asset asset = assets.get(i);

                // 获取用户持有该资产的数量
                Double holdingAmount = assetService.getAssetAmount(user.getUserId(), asset.getInvestmentId());
                int investmentId = asset.getInvestmentId();
                int assetId = asset.getAssetId();
                // 随机选取卖出份额，范围为0~持有份数，保留两位小数
                double amount = Double.parseDouble(String.format("%.2f", Math.random() * holdingAmount));

                if (holdingAmount < amount) {
                    request.setAttribute("message", "卖出失败，持有量不足");
                    try {
                        request.getRequestDispatcher("/WEB-INF/views/tradePage.jsp").forward(request, response);
                    } catch (Exception e) {
                        logger.error("Error in addSoldInvestmentRecord", e);
                    }
                    return;
                }

                assetService.addSoldInvestmentRecord(user.getUserId(), investmentId, assetId, amount);
                assetService.updateAsset(user.getUserId());
            }
        }
        showAdminIndexPage(request, response);
    }


    private void addRandomUser(HttpServletRequest request, HttpServletResponse response) {
        adminService.addRandomUser(5);
        showAdminIndexPage(request, response);
    }

    private void resetSystem(HttpServletRequest request, HttpServletResponse response) {
        adminService.resetSystem();
        showAdminIndexPage(request, response);
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
