package com.investtrackpro.dao;

import com.investtrackpro.model.Portfolio;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDao {

    private DataSource dataSource;

    public PortfolioDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Portfolio> findByUserId(Long userId) {
        List<Portfolio> portfolios = new ArrayList<>();
        String sql = "SELECT * FROM portfolios WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Portfolio portfolio = new Portfolio();
                    portfolio.setId(resultSet.getLong("id"));
                    portfolio.setUserId(resultSet.getLong("user_id"));
                    portfolio.setInvestmentId(resultSet.getLong("investment_id"));
                    // 设置其他属性
                    portfolios.add(portfolio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }

    public void addInvestment(Long userId, Long investmentId) {
        String sql = "INSERT INTO portfolios (user_id, investment_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, investmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeInvestment(Long userId, Long investmentId) {
        String sql = "DELETE FROM portfolios WHERE user_id = ? AND investment_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, investmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Portfolio generateReport(Long userId) {
        // 生成投资组合报告的逻辑
        // 假设生成一个简单的报告
        Portfolio portfolio = new Portfolio();
        // 设置报告属性
        return portfolio;
    }
}
