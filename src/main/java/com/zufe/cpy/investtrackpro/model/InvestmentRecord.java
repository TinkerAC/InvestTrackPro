package com.zufe.cpy.investtrackpro.model;

public class InvestmentRecord {
    private Long investmentRecordId;
    private Long investmentId;
    private Long userId;
    private Double amount;
    private String currency;
    private String type;
    private String status;
    private String createdAt;
    private String updatedAt;

    public InvestmentRecord() {
    }

    public InvestmentRecord(Long investmentId, Long userId, Double amount, String currency, String type, String status) {
        this.investmentId = investmentId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.status = status;
    }

    public Long getInvestmentRecordId() {
        return investmentRecordId;
    }

    public void setInvestmentRecordId(Long investmentRecordId) {
        this.investmentRecordId = investmentRecordId;
    }

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

