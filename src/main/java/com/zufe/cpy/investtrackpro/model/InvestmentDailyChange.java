package com.zufe.cpy.investtrackpro.model;


import java.sql.Date;
import java.sql.Timestamp;

public class InvestmentDailyChange {
    private int ChangeId;
    private int investmentId;
    private Double openingValue;
    private Double closingValue;
    private Double highValue;
    private Double lowValue;
    private Date date;
    private Double volume;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private double changePercent;
    private double changeValue;

    public InvestmentDailyChange() {
    }

    public InvestmentDailyChange(int changeId, int investmentId, Double openingValue, Double closingValue, Double highValue, Double lowValue, Date date, Double volume, Timestamp createdAt, Timestamp updatedAt, double changePercent, double changeValue) {
        ChangeId = changeId;
        this.investmentId = investmentId;
        this.openingValue = openingValue;
        this.closingValue = closingValue;
        this.highValue = highValue;
        this.lowValue = lowValue;
        this.date = date;
        this.volume = volume;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.changePercent = changePercent;
        this.changeValue = changeValue;
    }


    //getters and setters

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public int getChangeId() {
        return ChangeId;
    }

    public void setChangeId(int changeId) {
        ChangeId = changeId;
    }

    public int getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(int investmentId) {
        this.investmentId = investmentId;
    }

    public Double getOpeningValue() {
        return openingValue;
    }

    public void setOpeningValue(Double openingValue) {
        this.openingValue = openingValue;
    }

    public Double getClosingValue() {
        return closingValue;
    }

    public void setClosingValue(Double closingValue) {
        this.closingValue = closingValue;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }
}
