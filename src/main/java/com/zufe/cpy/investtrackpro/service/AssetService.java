package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.AssetDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.dao.UserDao;
import com.zufe.cpy.investtrackpro.model.Asset;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public boolean addBoughtInvestmentRecord(int userId, int investmentId, int assetId, double amount) {
        Double currentPrize = investmentDao.findById(investmentId).getCurrentValue();

        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(userId);
        investmentRecord.setAmount(amount);
        investmentRecord.setCurrentPrize(currentPrize);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation("买入");
        investmentRecord.setAssetId(assetId);


        if (!userDao.isExist(userId)) {
            logger.error("User ID does not exist: " + investmentRecord.getUserId());

        }

        boolean success = investmentRecordDao.insertInvestmentRecord(investmentRecord);
        updateAsset(userId);
        return success;


    }

    public boolean addSoldInvestmentRecord(int userId, int investmentId, int assetId, double amount) {

        Double currentPrize = investmentDao.findById(investmentId).getCurrentValue();
        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(userId);
        investmentRecord.setAmount(amount);
        investmentRecord.setCurrentPrize(currentPrize);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation("卖出");
        investmentRecord.setAssetId(assetId);

        if (!userDao.isExist(userId)) {
            logger.error("User ID does not exist: " + investmentRecord.getUserId());
        }

        boolean success = investmentRecordDao.insertInvestmentRecord(investmentRecord);
        updateAsset(userId);
        return success;
    }

    //返回资产id
    public int addAsset(Asset asset) {
        return assetDao.insertAsset(asset);
    }

    public void updateAsset(int userId) {
        List<Asset> assets = assetDao.findByUserId(userId);

        for (Asset asset : assets) {
            List<InvestmentRecord> records = investmentRecordDao.find(userId, asset.getInvestmentId());
            Double currentPrice = investmentDao.findById(asset.getInvestmentId()).getCurrentValue();

            // 计算持有收益和卖出收益
            Map<String, Double> profits = calculateProfits(records, currentPrice);

            //计算持有数量
            double amount = calculateAmount(records);

            // 更新资产信息
            asset.setHoldingProfit(profits.get("holdingProfit"));
            asset.setTotalSellRevenue(profits.get("sellRevenue"));
            asset.setAmount(amount);

            assetDao.updateAsset(asset);
        }

    }

    private double calculateAmount(List<InvestmentRecord> records) {
        Double amount = 0.0;
        for (InvestmentRecord record : records) {
            if ("买入".equals(record.getOperation())) {
                amount += record.getAmount();
            } else if ("卖出".equals(record.getOperation())) {
                amount -= record.getAmount();
            }
        }
        return amount;
    }


    //返回资产id
    public int getAssetId(int userId, int investmentId) {
        return assetDao.getAssetId(userId, investmentId);
    }

    //根据用户id返回交易记录
    public List<InvestmentRecord> getInvestmentRecordsByUserId(int userId) {
        return investmentRecordDao.findByUserId(userId);
    }

    public Double getAssetAmount(int userId, int investmentId) {
        return assetDao.getAssetAmount(userId, investmentId);
    }

    public Asset getAsset(int userId, int investmentId) {
        return assetDao.find(userId, investmentId);
    }


    public static Map<String, Double> calculateProfits(List<InvestmentRecord> records, double currentPrice) {
        double holdingProfit = 0.0;
        double sellRevenue = 0.0;
        Map<Integer, List<InvestmentRecord>> purchases = new HashMap<>();

        for (InvestmentRecord record : records) {
            if ("买入".equals(record.getOperation())) {
                purchases.putIfAbsent(record.getAssetId(), new ArrayList<>());
                purchases.get(record.getAssetId()).add(record);
                // 计算持有收益
                double profit = (currentPrice - record.getCurrentPrize()) * record.getAmount();
                holdingProfit += profit;
            } else if ("卖出".equals(record.getOperation())) {
                List<InvestmentRecord> purchaseList = purchases.get(record.getAssetId());
                double amountToSell = record.getAmount();

                while (amountToSell > 0 && purchaseList != null && !purchaseList.isEmpty()) {
                    InvestmentRecord purchase = purchaseList.get(0);
                    if (purchase.getAmount() <= amountToSell) {
                        double profit = (record.getCurrentPrize() - purchase.getCurrentPrize()) * purchase.getAmount();
                        sellRevenue += profit;
                        amountToSell -= purchase.getAmount();
                        purchaseList.remove(0);
                    } else {
                        double profit = (record.getCurrentPrize() - purchase.getCurrentPrize()) * amountToSell;
                        sellRevenue += profit;
                        purchase.setAmount(purchase.getAmount() - amountToSell);
                        amountToSell = 0;
                    }
                }
            }
        }

        Map<String, Double> result = new HashMap<>();
        result.put("holdingProfit", holdingProfit);
        result.put("sellRevenue", sellRevenue);
        return result;
    }

    private static class PurchaseRecord {
        double price;
        double amount;

        public PurchaseRecord(double price, double amount) {
            this.price = price;
            this.amount = amount;
        }
    }
}