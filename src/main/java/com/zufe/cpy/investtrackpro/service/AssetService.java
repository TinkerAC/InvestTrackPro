package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.AssetDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.dao.UserDao;
import com.zufe.cpy.investtrackpro.model.Asset;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class AssetService {

    private final InvestmentRecordDao investmentRecordDao = new InvestmentRecordDao();
    private final InvestmentDao investmentDao = new InvestmentDao();
    private final UserDao userDao = new UserDao();
    private final AssetDao assetDao = new AssetDao();
    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);


    public List<Asset> getAssetsByUserId(int userId) {
        return assetDao.findByUserId(userId);
    }

    public boolean isAssetExist(int userId, int investmentId) {
        return assetDao.isAssetExist(userId, investmentId);
    }

    public boolean addInvestmentRecord(HttpServletRequest request, String operation, int assetId) {
        User user = (User) request.getSession().getAttribute("user");
        int investmentId = Integer.parseInt(request.getParameter("investmentId"));
        Double currentPrize = investmentDao.findById(investmentId).getCurrentValue();


        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(Integer.parseInt(String.valueOf(user.getUserId())));
        investmentRecord.setAmount(Double.parseDouble(request.getParameter("amount")));
        investmentRecord.setCurrentPrize(currentPrize);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation(operation);
        investmentRecord.setAssetId(assetId);


        if (!userDao.isExist(user.getUserId())) {
            logger.error("User ID does not exist: " + investmentRecord.getUserId());

        }

        boolean success = investmentRecordDao.insertInvestmentRecord(investmentRecord);
        return success;

    }

    //返回资产id
    public int addAsset(Asset asset) {
        return assetDao.insertAsset(asset);
    }

    public void updateAsset(int userId) {
        List<Asset> assets = assetDao.findByUserId(userId);

        for (Asset asset : assets) {
            List<InvestmentRecord> investmentRecords = investmentRecordDao.find(userId, asset.getInvestmentId());

            // 计算持有量
            double amount = 0.0;
            // 计算总买入成本
            double totalCost = 0.0;
            // 计算总卖出收入
            double totalSellRevenue = 0.0;
            for (InvestmentRecord investmentRecord : investmentRecords) {
                if (investmentRecord.getOperation().equals("买入")) {
                    amount += investmentRecord.getAmount();
                    totalCost += investmentRecord.getAmount() * investmentRecord.getCurrentPrize();
                } else if (investmentRecord.getOperation().equals("卖出")) {
                    amount -= investmentRecord.getAmount();
                    totalSellRevenue += investmentRecord.getAmount() * investmentRecord.getCurrentPrize();
                    // 这里需要减去相应卖出的持有成本
                    totalCost -= investmentRecord.getAmount() * getAverageBuyPrice(investmentRecords, investmentRecord.getAmount());
                } else {
                    logger.error("Unknown operation: " + investmentRecord.getOperation());
                }
            }

            // 获取当前单价
            double currentPrice = investmentDao.findById(asset.getInvestmentId()).getCurrentValue();
            double currentMarketValue = amount * currentPrice;

            // 持有收益计算
            double holdingProfit = currentMarketValue - totalCost;

            // 总收益 = 已实现收益 + 持有收益
            double totalProfit = totalSellRevenue + holdingProfit;

            // 更新资产信息
            asset.setAmount(amount);
            asset.setHoldingProfit(holdingProfit);
            asset.setTotalSellRevenue(totalSellRevenue);

            assetDao.updateAsset(asset);
        }
    }

    /**
     * 获取卖出时的平均买入价格，用于减去相应的持有成本
     */
    private double getAverageBuyPrice(List<InvestmentRecord> records, double sellAmount) {
        double buyAmount = 0.0;
        double buyCost = 0.0;
        for (InvestmentRecord record : records) {
            if (record.getOperation().equals("买入")) {
                buyAmount += record.getAmount();
                buyCost += record.getAmount() * record.getCurrentPrize();
            }
        }
        return buyCost / buyAmount;
    }



    //返回资产id
    public int getAssetId(int userId, int investmentId) {
        return assetDao.getAssetId(userId, investmentId);
    }

    //根据用户id返回交易记录
    public List<InvestmentRecord> getInvestmentRecordsByUserId(int userId) {
        return investmentRecordDao.findByUserId(userId);
    }
}