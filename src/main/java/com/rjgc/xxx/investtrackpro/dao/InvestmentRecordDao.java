package com.rjgc.xxx.investtrackpro.dao;

import com.rjgc.xxx.investtrackpro.model.InvestmentRecord;
import com.rjgc.xxx.investtrackpro.util.DataBaseUtil;
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

        String sql = "INSERT INTO investment_record (investment_id, user_id, amount, operation,current_prize, status,asset_id) VALUES (?, ?,?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, investmentRecord.getInvestmentId());
            pstmt.setInt(2, investmentRecord.getUserId());
            pstmt.setBigDecimal(3, investmentRecord.getAmount());
            pstmt.setString(4, investmentRecord.getOperation());
            pstmt.setBigDecimal(5, investmentRecord.getCurrentPrize());
            pstmt.setString(6, investmentRecord.getStatus());
            pstmt.setInt(7, investmentRecord.getAssetId());
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

    public void deleteAll() {
        String sql = "DELETE FROM investment_record";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting all investment records", e);
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
                InvestmentRecord investmentRecord = new InvestmentRecord(
                        rs.getInt("investment_record_id"),
                        rs.getInt("investment_id"),
                        rs.getInt("user_id"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("current_prize"),
                        rs.getString("status"),
                        rs.getString("operation"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("asset_id")
                );
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
            ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    InvestmentRecord investmentRecord = new InvestmentRecord(
                            rs.getInt("investment_record_id"),
                            rs.getInt("investment_id"),
                            rs.getInt("user_id"),
                            rs.getBigDecimal("amount"),
                            rs.getBigDecimal("current_prize"),
                            rs.getString("status"),
                            rs.getString("operation"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at"),
                            rs.getInt("asset_id")
                    );

                    investmentRecords.add(investmentRecord);
                }
        } catch (SQLException e) {
            logger.error("Error finding investment records by user ID", e);
        }
        return investmentRecords;
    }

    // 查（按用户ID和投资ID）
    public List<InvestmentRecord> find(int userId, int investmentId) {
        List<InvestmentRecord> investmentRecords = new ArrayList<>();
        String sql = "SELECT * FROM investment_record WHERE user_id = ? and investment_id = ? order by created_at ";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, investmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvestmentRecord investmentRecord = new InvestmentRecord(
                            rs.getInt("investment_record_id"),
                            rs.getInt("investment_id"),
                            rs.getInt("user_id"),
                            rs.getBigDecimal("amount"),
                            rs.getBigDecimal("current_prize"),
                            rs.getString("status"),
                            rs.getString("operation"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at"),
                            rs.getInt("asset_id")
                    );
                    investmentRecords.add(investmentRecord);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding investment records by user ID", e);
        }
        return investmentRecords;
    }
}
