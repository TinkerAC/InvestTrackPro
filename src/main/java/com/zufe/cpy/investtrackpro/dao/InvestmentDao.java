package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvestmentDao {
    public Investment findById(int investmentId) {
        Connection conn = DBUtils.getConnection();
        String sql = "SELECT * FROM investment WHERE investment_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, investmentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Investment investment = new Investment();
                investment.setInvestmentId(rs.getInt("investment_id"));
                investment.setName(rs.getString("name"));
                investment.setDescription(rs.getString("description"));
                investment.setCategory(rs.getString("category"));
                investment.setExpectedReturn(rs.getBigDecimal("expected_return"));
                investment.setRiskLevel(rs.getBigDecimal("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investment.setInitialValue(rs.getBigDecimal("initial_value"));
                investment.setCurrentValue(rs.getBigDecimal("current_value"));
                return investment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeJDBC(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询所有投资项目
     *
     * @return 所有投资项目
     */
    public List<Investment> findAll() {
        Connection conn = DBUtils.getConnection();
        String sql = "SELECT * FROM investment";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Investment> investments = new ArrayList<>();
            while (rs.next()) {
                Investment investment = new Investment();
                investment.setInvestmentId(rs.getInt("investment_id"));
                investment.setName(rs.getString("name"));
                investment.setDescription(rs.getString("description"));
                investment.setCategory(rs.getString("category"));
                investment.setInitialValue(rs.getBigDecimal("initial_value"));
                investment.setCurrentValue(rs.getBigDecimal("current_value"));
                investment.setExpectedReturn(rs.getBigDecimal("expected_return"));
                investment.setRiskLevel(rs.getBigDecimal("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investments.add(investment);
            }
            return investments;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBUtils.closeJDBC(conn, ps, rs);
        }
        return null;
    }

    public List<Investment> search(Map<String, String> criteria) {
        Connection connection = DBUtils.getConnection();
        StringBuilder sql = new StringBuilder("SElECt * FROM investment");
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (!criteria.isEmpty()) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                switch (key) {
                    case "name":
                        sql.append("name LIKE '%").append(value).append("%' AND ");
                        break;
                    case "category":
                        sql.append("category='").append(value).append("' AND ");
                        break;
                    case "expected_return":
                        sql.append("expected_return>=").append(value).append(" AND ");
                        break;
                    case "risk_level":
                        sql.append("risk_level<=").append(value).append(" AND ");
                        break;
                    default:
                        break;
                }
                sql = new StringBuilder(sql.substring(0, sql.length() - 5));

            }
        }
        try {
            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            List<Investment> investments = new ArrayList<>();
            while (rs.next()) {
                Investment investment = new Investment();
                investment.setName(rs.getString("name"));
                investment.setDescription(rs.getString("description"));
                investment.setCategory(rs.getString("category"));
                investment.setExpectedReturn(rs.getBigDecimal("expected_return"));
                investment.setRiskLevel(rs.getBigDecimal("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investments.add(investment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeJDBC(connection, ps, rs);
        }
        return null;
    }

    public void save(Investment investment) {
        insert(investment);
    }

    public void insert(Investment investment) {
        Connection conn = DBUtils.getConnection();
        String sql = "INSERT INTO investment(name, description, category, expected_return, risk_level) VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, investment.getName());
            ps.setString(2, investment.getDescription());
            ps.setString(3, investment.getCategory());
            ps.setBigDecimal(4, investment.getExpectedReturn());
            ps.setBigDecimal(5, investment.getRiskLevel());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeJDBC(conn, ps, null);

        }
    }

    public void update(Investment investment) {

    }


    public void deleteById(Long id) {

    }


    public List<InvestmentRecord> findRecordsByInvestmentId(Long investmentId) {
        return null;
    }
}
