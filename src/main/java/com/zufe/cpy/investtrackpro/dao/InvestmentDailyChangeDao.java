package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.InvestmentDailyChange;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDailyChangeDao {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentDailyChangeDao.class);
    private static final String SELECT_ALL = "select * from investment_daily_change";

    public List<InvestmentDailyChange> findByInvestmentId(int investmentId) {
        List<InvestmentDailyChange> investmentDailyChanges = new ArrayList<>();
        Connection conn = DataBaseUtil.getConnection();
        String sql = "select * from investment_daily_change where investment_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, investmentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int changeId = rs.getInt("change_id");
                Double opening_value = rs.getDouble("opening_value");
                Double closing_value = rs.getDouble("closing_value");
                Double high_value = rs.getDouble("high_value");
                Double low_value = rs.getDouble("low_value");
                Date date = rs.getDate("date");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Double changePercent = rs.getDouble("change_percent");
                Double volume = rs.getDouble("volume");
                Double changeValue = rs.getDouble("change_value");

                InvestmentDailyChange investmentDailyChange = new InvestmentDailyChange(changeId, investmentId, opening_value, closing_value, high_value, low_value, date, volume, createdAt, changePercent, changeValue);
                investmentDailyChanges.add(investmentDailyChange);
            }
        } catch (Exception e) {


            logger.error("查询投资日变化数据失败", e);
            return investmentDailyChanges;
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return investmentDailyChanges;
    }

    public List<InvestmentDailyChange> findAll() {
        List<InvestmentDailyChange> investmentDailyChanges = new ArrayList<>();
        Connection conn = DataBaseUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_ALL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int changeId = rs.getInt("change_id");
                int investmentId = rs.getInt("investment_id");
                Double opening_value = rs.getDouble("opening_value");
                Double closing_value = rs.getDouble("closing_value");
                Double high_value = rs.getDouble("high_value");
                Double low_value = rs.getDouble("low_value");
                Date date = rs.getDate("date");
                Timestamp createAt = rs.getTimestamp("create_at");
                Double volume = rs.getDouble("volume");
                Double changePercent = rs.getDouble("change_percent");
                Double changeValue = rs.getDouble("change_value");
                InvestmentDailyChange investmentDailyChange = new InvestmentDailyChange(changeId, investmentId, opening_value, closing_value, high_value, low_value, date, volume, createAt, changePercent, changeValue);
                investmentDailyChanges.add(investmentDailyChange);
            }
        } catch (Exception e) {
            logger.error("查询投资日变化数据失败", e);
            return investmentDailyChanges;
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return investmentDailyChanges;
    }

    public void deleteAll() {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "delete from investment_daily_change";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error("删除投资日变化数据失败", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, null);
        }
    }


    public void insert(InvestmentDailyChange investmentDailyChange) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "insert into investment_daily_change(investment_id, opening_value, closing_value, high_value, low_value, date, volume, change_percent, change_value) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, investmentDailyChange.getInvestmentId());
            pstmt.setDouble(2, investmentDailyChange.getOpeningValue());
            pstmt.setDouble(3, investmentDailyChange.getClosingValue());
            pstmt.setDouble(4, investmentDailyChange.getHighValue());
            pstmt.setDouble(5, investmentDailyChange.getLowValue());
            pstmt.setDate(6, investmentDailyChange.getDate());
            pstmt.setDouble(7, investmentDailyChange.getVolume());
            pstmt.setDouble(8, investmentDailyChange.getChangePercent());
            pstmt.setDouble(9, investmentDailyChange.getChangeValue());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error("插入投资日变化数据失败", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, null);
        }
    }

    public InvestmentDailyChange findLatestByInvestmentId(int investmentId) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM investment_daily_change WHERE investment_id = ? ORDER BY created_at DESC LIMIT 1";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, investmentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                InvestmentDailyChange change = new InvestmentDailyChange(rs.getInt("change_id"), rs.getInt("investment_id"), rs.getDouble("opening_value"), rs.getDouble("closing_value"), rs.getDouble("high_value"), rs.getDouble("low_value"), rs.getDate("date"), rs.getDouble("volume"), rs.getTimestamp("created_at"), rs.getDouble("change_percent"), rs.getDouble("change_value"));
                return change;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("查询投资日变化数据失败", e);
            return null;
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
    }

}
