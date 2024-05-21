package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentRecordDao {


    public InvestmentRecordDao() {
    }


    //增
    public boolean insertInvestmentRecord(InvestmentRecord investmentRecord) {
        Connection conn = DataBaseUtil.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO investment_record (investment_id, user_id, amount, purchase_price, status) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, investmentRecord.getInvestmentId());
            pstmt.setInt(2, investmentRecord.getUserId());
            pstmt.setDouble(3, investmentRecord.getAmount());
            pstmt.setDouble(4, investmentRecord.getPurchasePrice());
            pstmt.setString(5, investmentRecord.getStatus());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, null);
        }
        return true;
    }

    //删
    public boolean deleteInvestmentRecord(int investmentRecordId) {
        Connection conn = DataBaseUtil.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM investment_record WHERE investment_record_id = ?");
            pstmt.setInt(1, investmentRecordId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, null);
        }
        return true;
    }

    //查
    public List<InvestmentRecord> findAll() {
        Connection conn = DataBaseUtil.getConnection();
        List<InvestmentRecord> investmentRecords = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM investment_record");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("InvestmentRecordId");
                int investmentId = rs.getInt("investmentId");
                int userId = rs.getInt("userId");
                double amount = rs.getDouble("amount");
                double purchasePrice = rs.getDouble("purchasePrice");
                String status = rs.getString("status");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");
                InvestmentRecord investmentRecord = new InvestmentRecord(id, investmentId, userId, amount, purchasePrice, status, createdAt, updatedAt);
                investmentRecords.add(investmentRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investmentRecords;
    }

    public List<InvestmentRecord> findByUserId(int userId) {
        Connection conn = DataBaseUtil.getConnection();
        List<InvestmentRecord> investmentRecords = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM investment_record WHERE user_id = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                InvestmentRecord investmentRecord = new InvestmentRecord();
                investmentRecord.setInvestmentRecordId(rs.getInt("investment_record_id"));
                investmentRecord.setInvestmentId(rs.getInt("investment_id"));
                investmentRecord.setUserId(userId);
                investmentRecord.setAmount(rs.getDouble("amount"));
                investmentRecord.setPurchasePrice(rs.getDouble("purchase_price"));
                investmentRecord.setStatus(rs.getString("status"));
                investmentRecord.setCreatedAt(rs.getTimestamp("created_at"));
                investmentRecord.setUpdatedAt(rs.getTimestamp("updated_at"));
                investmentRecords.add(investmentRecord);

            }
            return investmentRecords;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeJDBC(conn, ps, rs);
        }
        return null;
    }


}
