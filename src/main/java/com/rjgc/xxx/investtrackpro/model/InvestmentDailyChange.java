package com.rjgc.xxx.investtrackpro.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "investment_daily_change")
public class InvestmentDailyChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_id")
    private int ChangeId;

    @Column(name = "investment_id")
    private int investmentId;

    @Column(name = "opening_value")
    private BigDecimal openingValue;

    @Column(name = "closing_value")
    private BigDecimal closingValue;

    @Column(name = "high_value")
    private BigDecimal highValue;

    @Column(name = "low_value")
    private BigDecimal lowValue;

    @Column(name = "date")
    private Date date;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "change_percent")
    private BigDecimal changePercent;

    @Column(name = "change_value")
    private BigDecimal changeValue;

    //constructors
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
