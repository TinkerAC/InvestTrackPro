package com.zufe.cpy.investtrackpro.model;

import java.sql.Timestamp;

public class Asset {
    private int assetId;
    private int userId;
    private int investmentId;
    private double amount;
    private Timestamp createdAt;


    public Asset() {
    }

    public Asset(int assetId, int userId, int investmentId, double amount, Timestamp createdAt) {
        this.assetId = assetId;
        this.userId = userId;
        this.investmentId = investmentId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
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
}
