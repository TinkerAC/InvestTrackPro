package com.rjgc.cpy.investtrackpro.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rjgc.cpy.investtrackpro.model.Asset;
import com.rjgc.cpy.investtrackpro.model.Investment;
import com.rjgc.cpy.investtrackpro.model.InvestmentRecord;
import com.rjgc.cpy.investtrackpro.model.User;
import com.rjgc.cpy.investtrackpro.service.AdminService;
import com.rjgc.cpy.investtrackpro.service.AssetService;
import com.rjgc.cpy.investtrackpro.service.InvestmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private AssetService assetService;

    @GetMapping("/")
    public String showAdminIndexPage(Model model) {
        return "admin/admin"; // 返回视图名称
    }

    @GetMapping("/user")
    public String showUserList(Model model) {
        List<User> users = adminService.getUserList();
        model.addAttribute("users", users);
        return "admin/userList";
    }

    @GetMapping("/investment")
    public String showInvestmentList(Model model) {
        List<Investment> investments = investmentService.getInvestmentList();
        model.addAttribute("investments", investments);
        return "admin/investmentList";
    }

    @GetMapping("/investmentRecord")
    public String showInvestmentRecordList(Model model) {
        List<InvestmentRecord> investmentRecords = investmentService.getInvestmentRecordList();
        model.addAttribute("investmentRecords", investmentRecords);
        return "admin/investmentRecordList";
    }

    @PostMapping("/madeInHaven")
    public String madeInHaven(Model model) {
        adminService.madeInHaven();
        model.addAttribute("message", "模拟时间流逝成功!");
        return "admin/admin";
    }

    @PostMapping("/resetSystem")
    public String resetSystem(Model model) {
        adminService.resetSystem();
        model.addAttribute("message", "系统重置成功!");
        return "admin/admin";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @PostMapping("/randomUser")
    public String addRandomUser(Model model) {
        adminService.addRandomUser(5);
        model.addAttribute("message", "随机用户添加成功!");
        return "admin/admin";
    }

    @PostMapping("/randomBuy")
    public String addRandomBoughtInvestmentRecord(Model model) {
        List<User> users = adminService.getUserList();
        List<Investment> investments = investmentService.getInvestmentList();

        for (User user : users) {
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

                BigDecimal randomAmount = BigDecimal.valueOf(Math.random() * 15).setScale(2, RoundingMode.HALF_UP);

                boolean isSuccess = assetService.addBoughtInvestmentRecord(user.getUserId(), randomInvestment.getInvestmentId(), assetId, randomAmount);
                if (!isSuccess) {
                    logger.error("Failed to add bought investment record for user:{} ", user.getUserId());
                }
            }
        }

        model.addAttribute("message", "随机买入成功!");
        return "admin/admin";
    }

    @PostMapping("/randomSell")
    public String addRandomSellInvestmentRecord(Model model) {
        List<User> users = adminService.getUserList();

        for (User user : users) {
            List<Asset> assets = assetService.getAssetsByUserId(user.getUserId());
            Collections.shuffle(assets);

            int count = (int) (Math.random() * assets.size());
            for (int i = 0; i < count; i++) {
                Asset asset = assets.get(i);
                int investmentId = asset.getInvestmentId();
                int assetId = asset.getAssetId();

                BigDecimal holdingAmount = assetService.getAssetAmount(user.getUserId(), investmentId);
                BigDecimal randomAmount = BigDecimal.valueOf(Math.random() * holdingAmount.doubleValue()).setScale(2, RoundingMode.HALF_UP);

                if (holdingAmount.compareTo(randomAmount) >= 0) {
                    boolean sellSuccess = assetService.addSoldInvestmentRecord(user.getUserId(), investmentId, assetId, randomAmount);

                    if (sellSuccess) {
                        assetService.updateAsset(user.getUserId());
                    } else {
                        logger.error("Failed to add sold investment record for user:{} ", user.getUserId());
                    }
                }
            }
        }
        model.addAttribute("message", "随机卖出成功!");
        return "admin/admin";
    }

    @PostMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestBody JsonObject jsonObject) {
        int userId = Integer.parseInt(jsonObject.get("userId").getAsString());
        String phone = jsonObject.get("phone").getAsString();
        String userName = jsonObject.get("userName").getAsString();
        String address = jsonObject.get("address").getAsString();

        User user = new User();
        user.setUserId(userId);
        user.setPhone(phone);
        user.setUsername(userName);
        user.setAddress(address);

        boolean updateSuccess = adminService.updateUser(user);

        if (updateSuccess) {
            return ResponseEntity.ok("{\"status\":\"success\"}");
        } else {
            return ResponseEntity.status(500).body("{\"status\":\"error\"}");
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody JsonObject jsonObject) {
        int userId = Integer.parseInt(jsonObject.get("userId").getAsString());
        adminService.deleteUser(userId);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, Exception e) {
        logger.error("Error in AdminController", e);
        model.addAttribute("message", "系统错误，请联系管理员!");
        return "admin/admin";
    }
}
