package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.Asset;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetDao {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentRecordDao.class);

    public List<Asset> findByUserId(int userId) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM asset WHERE user_id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            List<Asset> assets = new ArrayList<>();
            while (rs.next()) {
                Asset asset = new Asset();
                asset.setAssetId(rs.getInt("asset_id"));
                asset.setUserId(rs.getInt("user_id"));
                asset.setInvestmentId(rs.getInt("investment_id"));
                asset.setAmount(rs.getDouble("amount"));
                asset.setCreatedAt(rs.getTimestamp("created_at"));
                assets.add(asset);
            }
            return assets;

        } catch (Exception e) {
            logger.error("Error finding asset by user_id", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return null;
    }

    //判断某用户(user_id)是否已经购买了某投资(investment_id)
    public boolean isAssetExist(int userId, int investmentId) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "SELECT * FROM asset WHERE user_id=? AND investment_id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, investmentId);

            rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            logger.error("Error finding asset by user_id and investment_id", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return false;
    }

    //新建资产,返回资产id,失败返回-1
    public int insertAsset(Asset asset) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "INSERT INTO asset(user_id, investment_id, amount) VALUES(?, ?, ?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, asset.getUserId());
            pstmt.setInt(2, asset.getInvestmentId());
            pstmt.setDouble(3, asset.getAmount());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            logger.error("Error inserting asset", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return -1;
    }


    //改
    public void updateAsset(Asset asset) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "UPDATE asset SET amount=?,update_at=? WHERE asset_id=?";

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, asset.getAmount());
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3, asset.getAssetId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            logger.error("Error updating asset", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, null);
        }

    }


    //从用户id和投资id获取资产id
    public int getAssetId(int userId, int investmentId) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "SELECT asset_id FROM asset WHERE user_id=? AND investment_id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, investmentId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("asset_id");
            }
        } catch (Exception e) {
            logger.error("Error getting asset_id", e);
        } finally {
            DataBaseUtil.closeJDBC(conn, pstmt, rs);
        }
        return -1;
    }


}
