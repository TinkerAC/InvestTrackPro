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

    //从交易记录中更新资产
    public void updateAsset(int userId) {
        List<Asset> assets = assetDao.findByUserId(userId);
        //遍历所有资产,将买入的资产加到资产中，将卖出的资产减去

        for (Asset asset : assets) {
            List<InvestmentRecord> investmentRecords = investmentRecordDao.find(userId, asset.getInvestmentId());
            Double amount = 0.0;
            for (InvestmentRecord investmentRecord : investmentRecords) {

                if (investmentRecord.getOperation() == null) {
                    continue;
                }
                if (investmentRecord.getOperation().equals("买入")) {
                    amount += investmentRecord.getAmount();
                } else if (investmentRecord.getOperation().equals("卖出")) {
                    amount -= investmentRecord.getAmount();

                }

            }
            asset.setAmount(amount);
            assetDao.updateAsset(asset);

        }


    }

    //返回资产id
    public int getAssetId(int userId, int investmentId) {
        return assetDao.getAssetId(userId, investmentId);
    }
}