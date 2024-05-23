package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvestmentRecordDao {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentRecordDao.class);

    public InvestmentRecordDao() {
    }



    // 增
    public boolean insertInvestmentRecord(InvestmentRecord investmentRecord) {

        String sql = "INSERT INTO investment_record (investment_id, user_id, amount, current_prize, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, investmentRecord.getInvestmentId());
            pstmt.setInt(2, investmentRecord.getUserId());
            pstmt.setDouble(3, investmentRecord.getAmount());
            pstmt.setDouble(4, investmentRecord.getCurrentPrize());
            pstmt.setString(5, investmentRecord.getStatus());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("Error inserting investment record", e);
            return false;
        }
    }

    // 删
    public boolean deleteInvestmentRecord(int investmentRecordId) {
        String sql = "DELETE FROM investment_record WHERE investment_record_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, investmentRecordId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("Error deleting investment record", e);
            return false;
        }
    }

    // 查（所有记录）
    public List<InvestmentRecord> findAll() {
        List<InvestmentRecord> investmentRecords = new ArrayList<>();
        String sql = "SELECT * FROM investment_record";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InvestmentRecord investmentRecord = new InvestmentRecord();
                investmentRecord.setInvestmentRecordId(rs.getInt("investment_record_id"));
                investmentRecord.setInvestmentId(rs.getInt("investment_id"));
                investmentRecord.setUserId(rs.getInt("user_id"));
                investmentRecord.setAmount(rs.getDouble("amount"));
                investmentRecord.setCurrentPrize(rs.getDouble("current_prize"));
                investmentRecord.setStatus(rs.getString("status"));
                investmentRecord.setCreatedAt(rs.getTimestamp("created_at"));
                investmentRecord.setUpdatedAt(rs.getTimestamp("updated_at"));
                investmentRecords.add(investmentRecord);
            }
        } catch (SQLException e) {
            logger.error("Error finding all investment records", e);
        }
        return investmentRecords;
    }

    // 查（按用户ID）
    public List<InvestmentRecord> findByUserId(int userId) {
        List<InvestmentRecord> investmentRecords = new ArrayList<>();
        String sql = "SELECT * FROM investment_record WHERE user_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvestmentRecord investmentRecord = new InvestmentRecord();
                    investmentRecord.setInvestmentRecordId(rs.getInt("investment_record_id"));
                    investmentRecord.setInvestmentId(rs.getInt("investment_id"));
                    investmentRecord.setUserId(userId);
                    investmentRecord.setAmount(rs.getDouble("amount"));
                    investmentRecord.setCurrentPrize(rs.getDouble("current_prize"));
                    investmentRecord.setStatus(rs.getString("status"));
                    investmentRecord.setCreatedAt(rs.getTimestamp("created_at"));
                    investmentRecord.setUpdatedAt(rs.getTimestamp("updated_at"));
                    investmentRecords.add(investmentRecord);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding investment records by user ID", e);
        }
        return investmentRecords;
    }
}
