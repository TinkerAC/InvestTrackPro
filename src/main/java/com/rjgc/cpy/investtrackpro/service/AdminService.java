package com.rjgc.cpy.investtrackpro.service;

import com.rjgc.cpy.investtrackpro.dao.*;
import com.rjgc.cpy.investtrackpro.model.Investment;
import com.rjgc.cpy.investtrackpro.model.InvestmentDailyChange;
import com.rjgc.cpy.investtrackpro.model.InvestmentRecord;
import com.rjgc.cpy.investtrackpro.model.User;
import com.rjgc.cpy.investtrackpro.util.SecurityUtil;
import com.rjgc.cpy.investtrackpro.util.DataBaseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private InvestmentDao investmentDao;

    @Autowired
    private InvestmentDailyChangeDao investmentDailyChangeDao;

    @Autowired
    private InvestmentRecordDao investmentRecordDao;

    @Autowired
    private AssetDao assetDao;

    @Autowired
    private AssetService assetService;

    private final Random random = new Random();

    @Transactional
    public void resetSystem() {
        try {
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
        } catch (Exception e) {
            logger.error("Error resetting system", e);
            throw e;
        }
    }

    public List<User> getUserList() {
        return userDao.findAllUsers();
    }

    @Transactional
    public void madeInHaven() {
        try {
            List<Investment> investments = investmentDao.findAll();
            for (Investment investment : investments) {
                BigDecimal currentValue = investment.getCurrentValue();
                int riskLevel = investment.getRiskLevel();

                BigDecimal maxChangePercent = BigDecimal.valueOf(1 + riskLevel * 0.5);
                BigDecimal changePercent = (BigDecimal.valueOf(random.nextDouble()).subtract(BigDecimal.valueOf(0.5)))
                        .multiply(BigDecimal.valueOf(2))
                        .multiply(maxChangePercent);

                BigDecimal changeAmount = currentValue.multiply(changePercent).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
                BigDecimal closingValue = currentValue.add(changeAmount);
                BigDecimal highValue = currentValue.max(closingValue).add(BigDecimal.valueOf(random.nextDouble()).multiply(maxChangePercent));
                BigDecimal lowValue = currentValue.min(closingValue).subtract(BigDecimal.valueOf(random.nextDouble()).multiply(maxChangePercent));
                BigDecimal volume = BigDecimal.valueOf(1000.0).add(BigDecimal.valueOf(random.nextDouble()).multiply(BigDecimal.valueOf(10000.0)));

                InvestmentDailyChange record = new InvestmentDailyChange();
                record.setInvestmentId(investment.getInvestmentId());
                record.setDate(Date.valueOf(LocalDate.now()));
                record.setOpeningValue(currentValue);
                record.setClosingValue(closingValue);
                record.setHighValue(highValue);
                record.setLowValue(lowValue);
                record.setVolume(volume);
                record.setChangePercent(changePercent);
                record.setChangeValue(changeAmount);

                investmentDailyChangeDao.insert(record);

                investment.setCurrentValue(closingValue);
                investment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                investmentDao.update(investment);
            }
        } catch (Exception e) {
            logger.error("Error in madeInHaven", e);
            throw e;
        }
    }

    public List<Investment> getInvestmentList() {
        return investmentDao.findAll();
    }

    public List<InvestmentRecord> getInvestmentRecordList() {
        return investmentRecordDao.findAll();
    }

    @Transactional
    public void addRandomUser(int count) throws SQLException {
        try (Connection conn = DataBaseUtil.getConnection()) {
            for (int i = 0; i < count; i++) {
                User user = new User();
                user.setRole("user");
                user.setEmail("user" + random.nextInt(1000) + "@qq.com");
                user.setPassword(SecurityUtil.hashPassword("123456"));
                if (userDao.findByEmail(user.getEmail()) != null) {
                    i -= 1;
                    continue;
                }
                userDao.insert(conn, user);
            }
        } catch (Exception e) {
            logger.error("Error adding random user", e);
            throw e;
        }
    }

    public User getAdmin() {
        return userDao.findAdmin();
    }

    public boolean updateUser(User user) {
        try {
            return userDao.updateUser(user);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            throw e;
        }
    }

    public void deleteUser(int userId) {
        try {
            userDao.deleteUser(userId);
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            throw e;
        }
    }
}
