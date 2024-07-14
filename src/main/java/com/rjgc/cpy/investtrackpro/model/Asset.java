package com.rjgc.cpy.investtrackpro.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private int assetId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "investment_id")
    private int investmentId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "holding_profit")
    private BigDecimal holdingProfit;

    @Column(name = "total_sell_revenue")
    private BigDecimal totalSellRevenue;


    //constructor
    public Asset() {
    }

    public Asset(int assetId, int userId, int investmentId, BigDecimal amount, Timestamp createdAt, BigDecimal holdingProfit, BigDecimal totalSellRevenue) {
        this.assetId = assetId;
        this.userId = userId;
        this.investmentId = investmentId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.holdingProfit = holdingProfit;
        this.totalSellRevenue = totalSellRevenue;
    }


    //getters and setters

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
