package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.dao.UserDao;
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
    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);


    public List<InvestmentRecord> getAssetsByUserId(int userId) {
        return investmentRecordDao.findByUserId(userId);
    }


    public void addInvestmentRecord(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute("user");

        int investmentId = Integer.parseInt(request.getParameter("investmentId"));
        Double currentPrize = investmentDao.findById(investmentId).getCurrentValue();

        InvestmentRecord investmentRecord = new InvestmentRecord();

        investmentRecord.setInvestmentId(investmentId);
        investmentRecord.setUserId(Integer.parseInt(String.valueOf(user.getUserId())));
        investmentRecord.setAmount(Double.parseDouble(request.getParameter("amount")));
        investmentRecord.setCurrentPrize(currentPrize);
        investmentRecord.setStatus("进行中");
        investmentRecord.setOperation("买入");

        if (!userDao.isExist(user.getUserId())) {

            logger.error("User ID does not exist: " + investmentRecord.getUserId());

        }
        boolean isSuccess = investmentRecordDao.insertInvestmentRecord(investmentRecord);

        if (isSuccess) {
            try {
                response.sendRedirect(request.getContextPath() + "/asset/view");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}