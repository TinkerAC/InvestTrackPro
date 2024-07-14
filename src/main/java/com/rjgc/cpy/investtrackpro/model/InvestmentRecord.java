package com.rjgc.cpy.investtrackpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "investment_record")
public class InvestmentRecord implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_record_id")
    private int investmentRecordId;

    @Column(name = "investment_id")
    private int investmentId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "current_prize")
    private BigDecimal currentPrize;//交易发生时的价格

    @Column(name = "operation")
    private String operation;//"买入"、"卖出"

    @Column(name = "status")
    private String status;//"买入":持有中,已卖出; "卖出":"无"

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "asset_id")
    private int assetId;


    // Constructors
    public InvestmentRecord() {
    }

    public InvestmentRecord(int investmentRecordId, int investmentId, int userId, BigDecimal amount, BigDecimal currentPrize, String status, String operation, Timestamp createdAt, Timestamp updatedAt, int assetId) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCurrentPrize() {
        return currentPrize;
    }

    public void setCurrentPrize(BigDecimal currentPrize) {
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

    @Override
    public InvestmentRecord clone() {
        try {
            InvestmentRecord clone = (InvestmentRecord) super.clone();
            // Deep copy mutable state here
            clone.amount = new BigDecimal(this.amount.toString());
            clone.currentPrize = new BigDecimal(this.currentPrize.toString());
            clone.createdAt = (Timestamp) this.createdAt.clone();
            clone.updatedAt = (Timestamp) this.updatedAt.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

