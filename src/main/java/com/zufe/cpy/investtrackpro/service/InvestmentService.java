package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentRecordDao;
import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;

import java.util.List;
import java.util.Map;

public class InvestmentService {

    private final InvestmentDao investmentDao;
    private final InvestmentRecordDao investmentRecordDao;

    public InvestmentService() {
        investmentDao = new InvestmentDao();
        investmentRecordDao = new InvestmentRecordDao();
    }

    public List<Investment> getAllInvestments() {
        return investmentDao.findAll();
    }

    public List<Investment> searchInvestments(Map<String, String> criteria) {
        // 实现根据条件筛选的逻辑
        return investmentDao.search(criteria);
    }

    public Investment getInvestmentById(int id) {
        return investmentDao.findById(id);
    }

    public List<InvestmentRecord> getInvestmentRecordList() {
        return investmentRecordDao.findAll();
    }


    public List<Investment> getInvestmentList() {
        return investmentDao.findAll();
    }
}
