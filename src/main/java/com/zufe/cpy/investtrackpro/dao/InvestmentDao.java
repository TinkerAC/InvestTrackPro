package com.zufe.cpy.investtrackpro.dao;

import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentDailyChange;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvestmentDao {
    public Investment findById(int investmentId) {
        Connection conn = DataBaseUtil.getConnection();
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
                investment.setRiskLevel(rs.getInt("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investment.setInitialValue(rs.getBigDecimal("initial_value"));
                investment.setCurrentValue(rs.getBigDecimal("current_value"));
                return investment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeJDBC(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询所有投资项目
     *
     * @return 所有投资项目
     */
    public List<Investment> findAll() {
        Connection conn = DataBaseUtil.getConnection();
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
                investment.setRiskLevel(rs.getInt("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investments.add(investment);
            }
            return investments;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DataBaseUtil.closeJDBC(conn, ps, rs);
        }
        return null;
    }

    public List<Investment> search(Map<String, String> criteria) {

        // SQL字段名映射
        Map<String, String> sqlVar = Map.of(
                "investmentId", "investment_id",
                "name", "name",
                "description", "description",
                "category", "category",
                "initialValue", "initial_value",
                "currentValue", "current_value",
                "expectedReturn", "expected_return",
                "riskLevel", "risk_level",
                "createdAt", "created_at",
                "updatedAt", "updated_at"
        );


        Connection connection = DataBaseUtil.getConnection();
        StringBuilder sql = new StringBuilder("SELECT * FROM investment");
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Investment> investments = new ArrayList<>();

        //构建PreparedStatement sql语句
        if (criteria != null && !criteria.isEmpty()) {
            sql.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                if (i > 0) {
                    sql.append(" AND ");
                }
                sql.append(sqlVar.get(entry.getKey())).append(" = ?");

                i++;
            }
        }


        try {
            ps = connection.prepareStatement(sql.toString());
            int index = 1;

            //设置PreparedStatement参数
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                ps.setString(index, entry.getValue());
                index++;
            }
            System.out.println(ps.toString());

            rs = ps.executeQuery();

            while (rs.next()) {
                Investment investment = new Investment();

                investment.setInvestmentId(rs.getInt("investment_id"));
                investment.setName(rs.getString("name"));
                investment.setDescription(rs.getString("description"));
                investment.setCategory(rs.getString("category"));
                investment.setExpectedReturn(rs.getBigDecimal("expected_return"));
                investment.setInitialValue(rs.getBigDecimal("initial_value"));
                investment.setCurrentValue(rs.getBigDecimal("current_value"));
                investment.setRiskLevel(rs.getInt("risk_level"));
                investment.setCreatedAt(rs.getTimestamp("created_at"));
                investments.add(investment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeJDBC(connection, ps, rs);
        }

        return investments;
    }


    public void save(Investment investment) {
        insert(investment);
    }

    public void insert(Investment investment) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "INSERT INTO investment(name, description, category, expected_return, risk_level) VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, investment.getName());
            ps.setString(2, investment.getDescription());
            ps.setString(3, investment.getCategory());
            ps.setBigDecimal(4, investment.getExpectedReturn());
            ps.setInt(5, investment.getRiskLevel());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeJDBC(conn, ps, null);

        }
    }

    public void update(Investment investment) {
        Connection conn = DataBaseUtil.getConnection();
        String sql = "UPDATE investment SET name=?, description=?, category=?, expected_return=?, risk_level=?, initial_value=?, current_value=? WHERE investment_id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, investment.getName());
            ps.setString(2, investment.getDescription());
            ps.setString(3, investment.getCategory());
            ps.setBigDecimal(4, investment.getExpectedReturn());
            ps.setInt(5, investment.getRiskLevel());
            ps.setBigDecimal(6, investment.getInitialValue());
            ps.setBigDecimal(7, investment.getCurrentValue());
            ps.setInt(8, investment.getInvestmentId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeJDBC(conn, ps, null);
        }
    }




}
