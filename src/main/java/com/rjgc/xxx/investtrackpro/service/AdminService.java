package com.rjgc.xxx.investtrackpro.service;

import com.rjgc.xxx.investtrackpro.model.Investment;
import com.rjgc.xxx.investtrackpro.model.InvestmentDailyChange;
import com.rjgc.xxx.investtrackpro.model.InvestmentRecord;
import com.rjgc.xxx.investtrackpro.model.User;
import com.rjgc.xxx.investtrackpro.util.SecurityUtil;
import com.rjgc.xxx.investtrackpro.util.DataBaseUtil;
import com.rjgc.xxx.investtrackpro.dao.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
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
    private final AssetDao assetDao = new AssetDao();
    private final AssetService assetService = new AssetService();

    public void resetSystem() {
        List<Investment> investments = investmentDao.findAll();
        for (Investment investment : investments) {
            investment.setCurrentValue(BigDecimal.valueOf(100.0));
            investment.setInitialValue(BigDecimal.valueOf(100.0));
            investment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            investmentDao.update(investment);
        }
        investmentRecordDao.deleteAll();
        investmentDailyChangeDao.deleteAll();
        assetDao.deleteAll();
        userDao.deleteAll();


    }

    public List<User> getUserList() {
        return userDao.findAllUsers();
    }

    public void madeInHaven() {
        List<Investment> investments = investmentDao.findAll();

        for (Investment investment : investments) {
            BigDecimal currentValue = investment.getCurrentValue();
            int riskLevel = investment.getRiskLevel();

            BigDecimal openingValue = currentValue;
            BigDecimal maxChangePercent = BigDecimal.valueOf(1 + riskLevel * 0.5); // 基于风险等级调整波动范围
            BigDecimal changePercent = (BigDecimal.valueOf(random.nextDouble()).subtract(BigDecimal.valueOf(0.5)))
                    .multiply(BigDecimal.valueOf(2))
                    .multiply(maxChangePercent); // 根据风险等级调整波动百分比

            BigDecimal changeAmount = openingValue.multiply(changePercent).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            BigDecimal closingValue = openingValue.add(changeAmount);
            BigDecimal highValue = openingValue.max(closingValue).add(BigDecimal.valueOf(random.nextDouble()).multiply(maxChangePercent));
            BigDecimal lowValue = openingValue.min(closingValue).subtract(BigDecimal.valueOf(random.nextDouble()).multiply(maxChangePercent));
            BigDecimal volume = BigDecimal.valueOf(1000.0).add(BigDecimal.valueOf(random.nextDouble()).multiply(BigDecimal.valueOf(10000.0)));

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

    public void addRandomUser(int count) {
        Connection conn = DataBaseUtil.getConnection();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setRole("user");
            user.setEmail("user" + random.nextInt(1000) + "@qq.com");
            user.setPassword(SecurityUtil.hashPassword("123456"));
            if (userDao.findByEmail(user.getEmail()) != null) {
                i -= 1; // 生成重复的 email 时，重新尝试生成用户
                continue;
            }
            userDao.insert(conn, user);
        }
    }

    public User getAdmin() {
        return userDao.findAdmin();
    }


    public boolean updateUser(User user) {


        return userDao.updateUser(user);
    }

    public void deleteUser(int userId) {

        userDao.deleteUser(userId);

    }
}
