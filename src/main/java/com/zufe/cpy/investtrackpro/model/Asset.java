package com.zufe.cpy.investtrackpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Asset {
    private int assetId;
    private int userId;
    private int investmentId;
    private BigDecimal amount;
    private Timestamp createdAt;
    private BigDecimal holdingProfit;
    private BigDecimal  totalSellRevenue;

    public Asset() {
    }

    public Asset(int assetId, int userId, int investmentId, BigDecimal amount, Timestamp createdAt,  BigDecimal holdingProfit, BigDecimal totalSellRevenue) {
        this.assetId = assetId;
        this.userId = userId;
        this.investmentId = investmentId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.holdingProfit = holdingProfit;
        this.totalSellRevenue = totalSellRevenue;
    }

    public int getAssetId() {
        return assetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(int investmentId) {
        this.investmentId = investmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }



    public BigDecimal getHoldingProfit() {
        return holdingProfit;
    }

    public void setHoldingProfit(BigDecimal holdingProfit) {
        this.holdingProfit = holdingProfit;
    }

    public BigDecimal getTotalSellRevenue() {
        return totalSellRevenue;
    }

    public void setTotalSellRevenue(BigDecimal totalSellRevenue) {
        this.totalSellRevenue = totalSellRevenue;
    }
}
