package com.rjgc.cpy.investtrackpro.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class InvestmentDailyChange {
    private int ChangeId;
    private int investmentId;
    private BigDecimal openingValue;
    private BigDecimal closingValue;
    private BigDecimal highValue;
    private BigDecimal lowValue;
    private Date date;
    private BigDecimal volume;
    private Timestamp createdAt;
    private BigDecimal changePercent;
    private BigDecimal changeValue;

    public InvestmentDailyChange() {
    }

    public InvestmentDailyChange(int changeId, int investmentId, BigDecimal openingValue, BigDecimal closingValue, BigDecimal highValue, BigDecimal lowValue, Date date, BigDecimal volume, Timestamp createdAt, BigDecimal changePercent, BigDecimal changeValue) {
        ChangeId = changeId;
        this.investmentId = investmentId;
        this.openingValue = openingValue;
        this.closingValue = closingValue;
        this.highValue = highValue;
        this.lowValue = lowValue;
        this.date = date;
        this.volume = volume;
        this.createdAt = createdAt;

        this.changePercent = changePercent;
        this.changeValue = changeValue;
    }


    //getters and setters

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
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

    public BigDecimal getOpeningValue() {
        return openingValue;
    }

    public void setOpeningValue(BigDecimal openingValue) {
        this.openingValue = openingValue;
    }

    public BigDecimal getClosingValue() {
        return closingValue;
    }

    public void setClosingValue(BigDecimal closingValue) {
        this.closingValue = closingValue;
    }

    public BigDecimal getHighValue() {
        return highValue;
    }

    public void setHighValue(BigDecimal highValue) {
        this.highValue = highValue;
    }

    public BigDecimal getLowValue() {
        return lowValue;
    }

    public void setLowValue(BigDecimal lowValue) {
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


    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }
}
