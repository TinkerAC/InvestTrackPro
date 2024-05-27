package com.zufe.cpy.investtrackpro.model;

import java.sql.Timestamp;

public class Asset {
    private int assetId;
    private int userId;
    private int investmentId;
    private double amount;
    private Timestamp createdAt;
    private Double holdingProfit;
    private Double totalSellRevenue;

    public Asset() {
    }

    public Asset(int assetId, int userId, int investmentId, double amount, Timestamp createdAt,  Double holdingProfit, Double totalSellRevenue) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }



    public Double getHoldingProfit() {
        return holdingProfit;
    }

    public void setHoldingProfit(Double holdingProfit) {
        this.holdingProfit = holdingProfit;
    }

    public Double getTotalSellRevenue() {
        return totalSellRevenue;
    }

    public void setTotalSellRevenue(Double totalSellRevenue) {
        this.totalSellRevenue = totalSellRevenue;
    }
}
