package com.zufe.cpy.investtrackpro.service;


import com.zufe.cpy.investtrackpro.model.InvestmentRecord;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;


import java.util.List;


public class PortfolioService {

    private final InvestmentRecordDao investmentRecordDao = new InvestmentRecordDao();

    public List<InvestmentRecord> getPortfolioByUserId(int userId) {
        return investmentRecordDao.findByUserId(userId);
    }

    public void addInvestmentRecord(InvestmentRecord investmentRecord) {
        investmentRecordDao.insertInvestmentRecord(investmentRecord);
    }


}