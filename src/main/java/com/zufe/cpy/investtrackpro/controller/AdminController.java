package com.zufe.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
            case "/editUser":
                editUser(request, response);
                break;
            case "/deleteUser":
                deleteUser(request, response);
                break;
            default:
                showNotFoundPage(request, response);
                break;

        }


    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {

        // 读取JSON数据
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error("传入数据错误");
            return;
        }
        // 将JSON数据转换为Java对象
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

        int userId = Integer.parseInt(jsonObject.get("userId").getAsString());


        adminService.deleteUser(userId);
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 读取JSON数据
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error("传入数据错误");
            return;
        }
        // 将JSON数据转换为Java对象
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

        // 获取数据字段
        int userId = Integer.parseInt(jsonObject.get("userId").getAsString());
        String phone = jsonObject.get("phone").getAsString();
        String userName = jsonObject.get("userName").getAsString();
        String address = jsonObject.get("address").getAsString();

        // 处理数据（例如更新数据库）
        User user = new User();
        user.setUserId(userId);
        user.setPhone(phone);
        user.setUsername(userName);
        user.setAddress(address);

        boolean updateSuccess = adminService.updateUser(user);

        // 发送响应
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (updateSuccess) {
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            response.getWriter().write("{\"status\":\"error\"}");
        }


    }

    // 为每个用户随机买入5次随机份额的随机投资
    private void addRandomBoughtInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = adminService.getUserList();
        List<Investment> investments = investmentService.getInvestmentList();

        for (User user : users) {
            // 每个用户随机买入5次
            Collections.shuffle(investments);
            for (int i = 0; i < 5; i++) {
                Investment randomInvestment = investments.get(i);
                boolean isAssetExist = assetService.isAssetExist(user.getUserId(), randomInvestment.getInvestmentId());

                int assetId;
                if (!isAssetExist) {
                    Asset asset = new Asset();
                    asset.setUserId(user.getUserId());
                    asset.setInvestmentId(randomInvestment.getInvestmentId());
                    asset.setAmount(BigDecimal.ZERO);
                    assetId = assetService.addAsset(asset);
                } else {
                    assetId = assetService.getAssetId(user.getUserId(), randomInvestment.getInvestmentId());
                }

                if (assetId == -1) {
                    continue;
                }

                // 随机买入0~15份，保留两位小数
                BigDecimal randomAmount = BigDecimal.valueOf(Math.random() * 15).setScale(2, RoundingMode.HALF_UP);

                boolean isSuccess = assetService.addBoughtInvestmentRecord(user.getUserId(), randomInvestment.getInvestmentId(), assetId, randomAmount);
                if (!isSuccess) {
                    logger.error("Failed to add bought investment record for user:{} ", user.getUserId());
                }
            }
        }


        request.setAttribute("message", "随机买入成功!");
        showAdminIndexPage(request, response);
    }

    // 为每个用户随机卖出部分持有的投资
    private void addRandomSellInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = adminService.getUserList();

        for (User user : users) {
            List<Asset> assets = assetService.getAssetsByUserId(user.getUserId());
            Collections.shuffle(assets);

            int count = (int) (Math.random() * assets.size());
            for (int i = 0; i < count; i++) {
                Asset asset = assets.get(i);
                int investmentId = asset.getInvestmentId();
                int assetId = asset.getAssetId();

                // 获取最新持有量
                BigDecimal holdingAmount = assetService.getAssetAmount(user.getUserId(), investmentId);
                BigDecimal randomAmount = BigDecimal.valueOf(Math.random() * holdingAmount.doubleValue()).setScale(2, RoundingMode.HALF_UP);

                // 确保不会卖出超过持有量
                if (holdingAmount.compareTo(randomAmount) >= 0) {
                    // 添加卖出记录
                    boolean sellSuccess = assetService.addSoldInvestmentRecord(user.getUserId(), investmentId, assetId, randomAmount);

                    if (sellSuccess) {
                        assetService.updateAsset(user.getUserId());
                    } else {
                        logger.error("Failed to add sold investment record for user:{} ", user.getUserId());
                    }
                }
            }
        }
        request.setAttribute("message", "随机卖出成功!");
        showAdminIndexPage(request, response);
    }


    private void addRandomUser(HttpServletRequest request, HttpServletResponse response) {
        adminService.addRandomUser(5);
        showAdminIndexPage(request, response);
    }

    private void resetSystem(HttpServletRequest request, HttpServletResponse response) {
        adminService.resetSystem();
        request.setAttribute("message", "系统重置成功!");
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
