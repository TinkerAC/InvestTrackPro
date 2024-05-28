package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.AssetDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.dao.UserDao;
import com.zufe.cpy.investtrackpro.model.Asset;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

    public boolean addBoughtInvestmentRecord(int userId, int investmentId, int assetId, BigDecimal amount) {
        BigDecimal currentPrice = investmentDao.findById(investmentId).getCurrentValue();

        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(userId);
        investmentRecord.setAmount(amount);
        investmentRecord.setCurrentPrize(currentPrice);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation("买入");
        investmentRecord.setAssetId(assetId);

        if (!userDao.isExist(userId)) {
            logger.error("User ID does not exist: " + investmentRecord.getUserId());
            return false;
        }

        boolean success = investmentRecordDao.insertInvestmentRecord(investmentRecord);
        if (success) {
            updateAsset(userId);
        }
        return success;
    }

    public boolean addSoldInvestmentRecord(int userId, int investmentId, int assetId, BigDecimal amount) {
        BigDecimal currentPrice = investmentDao.findById(investmentId).getCurrentValue();

        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(userId);
        investmentRecord.setAmount(amount);
        investmentRecord.setCurrentPrize(currentPrice);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation("卖出");
        investmentRecord.setAssetId(assetId);

        if (!userDao.isExist(userId)) {
            logger.error("User ID does not exist: " + investmentRecord.getUserId());
            return false;
        }

        boolean success = investmentRecordDao.insertInvestmentRecord(investmentRecord);
        if (success) {
            updateAsset(userId);
        }
        return success;
    }

    public int addAsset(Asset asset) {
        return assetDao.insertAsset(asset);
    }

    public void updateAsset(int userId) {
        List<Asset> assets = assetDao.findByUserId(userId);

        for (Asset asset : assets) {
            List<InvestmentRecord> records = investmentRecordDao.find(userId, asset.getInvestmentId());
            BigDecimal currentPrice = investmentDao.findById(asset.getInvestmentId()).getCurrentValue();

            // 深拷贝 InvestmentRecord 对象
            List<InvestmentRecord> recordsCopy = new ArrayList<>();
            for (InvestmentRecord record : records) {
                recordsCopy.add(record.clone());
            }

            // 计算利润和金额
            Map<String, BigDecimal> profits = calculateProfits(recordsCopy, currentPrice);
            BigDecimal amount = calculateAmount(records);

            // 更新资产信息
            asset.setHoldingProfit(profits.get("holdingProfit"));
            asset.setTotalSellRevenue(profits.get("sellRevenue"));
            asset.setAmount(amount);

            // 保存更新后的资产
            assetDao.updateAsset(asset);
        }
    }


    private BigDecimal calculateAmount(List<InvestmentRecord> records) {
        BigDecimal amount = BigDecimal.ZERO;
        for (InvestmentRecord record : records) {
            if ("买入".equals(record.getOperation())) {
                amount = amount.add(record.getAmount());
            } else if ("卖出".equals(record.getOperation())) {
                amount = amount.subtract(record.getAmount());
            }
        }
        return amount;
    }

    public int getAssetId(int userId, int investmentId) {
        return assetDao.getAssetId(userId, investmentId);
    }

    public List<InvestmentRecord> getInvestmentRecordsByUserId(int userId) {
        return investmentRecordDao.findByUserId(userId);
    }

    public BigDecimal getAssetAmount(int userId, int investmentId) {
        return assetDao.getAssetAmount(userId, investmentId);
    }

    public Asset getAsset(int userId, int investmentId) {
        return assetDao.find(userId, investmentId);
    }

    public static Map<String, BigDecimal> calculateProfits(List<InvestmentRecord> records, BigDecimal currentPrice) {
        BigDecimal holdingProfit = BigDecimal.ZERO;
        BigDecimal sellRevenue = BigDecimal.ZERO;
        Map<Integer, List<InvestmentRecord>> purchases = new HashMap<>();

        for (InvestmentRecord record : records) {
            if ("买入".equals(record.getOperation())) {
                purchases.putIfAbsent(record.getAssetId(), new ArrayList<>());
                purchases.get(record.getAssetId()).add(record);
                BigDecimal profit = (currentPrice.subtract(record.getCurrentPrize())).multiply(record.getAmount());
                holdingProfit = holdingProfit.add(profit);
            } else if ("卖出".equals(record.getOperation())) {
                List<InvestmentRecord> purchaseList = purchases.get(record.getAssetId());
                BigDecimal amountToSell = record.getAmount();

                while (amountToSell.compareTo(BigDecimal.ZERO) > 0 && purchaseList != null && !purchaseList.isEmpty()) {
                    InvestmentRecord purchase = purchaseList.get(0);
                    if (purchase.getAmount().compareTo(amountToSell) <= 0) {
                        BigDecimal profit = (record.getCurrentPrize().subtract(purchase.getCurrentPrize())).multiply(purchase.getAmount());
                        sellRevenue = sellRevenue.add(profit);
                        amountToSell = amountToSell.subtract(purchase.getAmount());
                        purchaseList.remove(0);
                    } else {
                        BigDecimal profit = (record.getCurrentPrize().subtract(purchase.getCurrentPrize())).multiply(amountToSell);
                        sellRevenue = sellRevenue.add(profit);
                        purchase.setAmount(purchase.getAmount().subtract(amountToSell));
                        amountToSell = BigDecimal.ZERO;
                    }
                }
            }
        }

        Map<String, BigDecimal> result = new HashMap<>();
        result.put("holdingProfit", holdingProfit);
        result.put("sellRevenue", sellRevenue);
        return result;
    }
}
