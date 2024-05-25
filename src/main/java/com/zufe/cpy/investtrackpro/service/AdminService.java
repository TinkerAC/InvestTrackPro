package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.InvestmentDailyChangeDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.dao.UserDao;
import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentDailyChange;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


public class AdminService {
    private final UserDao userDao = new UserDao();
    private final InvestmentDao investmentDao = new InvestmentDao();
    private final Random random = new Random();
    private final InvestmentDailyChangeDao investmentDailyChangeDao = new InvestmentDailyChangeDao();
    private final InvestmentRecordDao investmentRecordDao = new InvestmentRecordDao();

    public void resetSystem() {
        List<Investment> investments = investmentDao.findAll();
        for (Investment investment : investments) {
            investment.setCurrentValue(100.0);
            investment.setInitialValue(100.0);
            investment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            investmentDao.update(investment);
        }
        investmentRecordDao.deleteAll();
        investmentDailyChangeDao.deleteAll();
    }


    public List<User> getUserList() {
        return userDao.findAll();
    }

    public void madeInHaven() {
        List<Investment> investments = investmentDao.findAll();

        for (Investment investment : investments) {
            double currentValue = investment.getCurrentValue();
            int riskLevel = investment.getRiskLevel();

            double openingValue = currentValue;
            double maxChangePercent = 1 + riskLevel * 0.5; // 基于风险等级调整波动范围
            double changePercent = (random.nextDouble() - 0.5) * 2 * maxChangePercent; // 根据风险等级调整波动百分比

            double changeAmount = openingValue * (changePercent / 100);
            double closingValue = openingValue + changeAmount;
            double highValue = Math.max(openingValue, closingValue) + random.nextDouble() * maxChangePercent;
            double lowValue = Math.min(openingValue, closingValue) - random.nextDouble() * maxChangePercent;
            double volume = 1000.0 + random.nextDouble() * 10000.0;//随机生成交易量


            // 创建并设置 InvestmentDailyChange 对象的属性
            InvestmentDailyChange record = new InvestmentDailyChange();
            record.setInvestmentId(investment.getInvestmentId());
            record.setDate(Date.valueOf(LocalDate.now()));
            record.setOpeningValue(openingValue);
            record.setClosingValue(closingValue);
            record.setHighValue(highValue);
            record.setLowValue(lowValue);
            record.setVolume(volume);
            record.setChangePercent(changePercent);
            record.setChangeValue(changeAmount);

            // 插入记录到数据库
            investmentDailyChangeDao.insert(record);

            // 更新 investment 的当前值
            investment.setCurrentValue(closingValue);
            investment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            investmentDao.update(investment);
        }
    }

    public List<Investment> getInvestmentList() {
        return investmentDao.findAll();
    }

    public List<InvestmentRecord> getInvestmentRecordList() {
        return investmentRecordDao.findAll();
    }
}
