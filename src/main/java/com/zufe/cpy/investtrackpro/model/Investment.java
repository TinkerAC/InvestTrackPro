package com.zufe.cpy.investtrackpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Investment {
    private int investmentId;
    private String name;
    private String description;
    private String category;
    private double initialValue;
    private double currentValue;
    private double expectedReturn;
    private int riskLevel;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Investment() {
    }

    public Investment(int investmentId, String name, String description, String category, double initialValue, double currentValue, double expectedReturn, int riskLevel, Timestamp createdAt, Timestamp updatedAt) {
        this.investmentId = investmentId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.initialValue = initialValue;
        this.currentValue = currentValue;
        this.expectedReturn = expectedReturn;
        this.riskLevel = riskLevel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getInvestmentId() {
        return investmentId;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Double initialValue) {
        this.initialValue = initialValue;
    }

    public void setInvestmentId(int investmentId) {
        this.investmentId = investmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(Double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
