package com.zufe.cpy.investtrackpro.model;

import java.sql.Timestamp;

public class InvestmentRecord {
    private int investmentRecordId;
    private int investmentId;
    private int userId;
    private Double amount;
    private Double purchasePrice;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    // Constructors
    public InvestmentRecord() {
    }

    public InvestmentRecord(int investmentRecordId, int investmentId, int userId, Double amount, Double purchasePrice, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.investmentRecordId = investmentRecordId;
        this.investmentId = investmentId;
        this.userId = userId;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

