package com.zufe.cpy.investtrackpro.model;

import java.sql.Timestamp;

public class InvestmentRecord {
    private int investmentRecordId;
    private int investmentId;
    private int userId;
    private Double amount;
    private Double currentPrize;//交易发生时的价格
    private String operation;//"买入"、"卖出"
    private String status;//"买入":持有中,已卖出; "卖出":"无"
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int assetId;


    // Constructors
    public InvestmentRecord() {
    }

    public InvestmentRecord(int investmentRecordId, int investmentId, int userId, Double amount, Double currentPrize, String status, String operation, Timestamp createdAt, Timestamp updatedAt, int assetId) {
        this.investmentRecordId = investmentRecordId;
        this.investmentId = investmentId;
        this.userId = userId;
        this.amount = amount;
        this.currentPrize = currentPrize;
        this.status = status;
        this.operation = operation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assetId = assetId;
    }


    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInvestmentRecordId() {
        return investmentRecordId;
    }

    public void setInvestmentRecordId(int investmentRecordId) {
        this.investmentRecordId = investmentRecordId;
    }

    public int getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(int investmentId) {
        this.investmentId = investmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCurrentPrize() {
        return currentPrize;
    }

    public void setCurrentPrize(Double currentPrize) {
        this.currentPrize = currentPrize;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }
}

