package com.zufe.cpy.investtrackpro.service;

import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentRecord;

import java.util.List;
import java.util.Map;

public class InvestmentService {

    private final InvestmentDao investmentDao;

    public InvestmentService() {
        investmentDao = new InvestmentDao();
    }

    public List<Investment> getAllInvestments() {
        return investmentDao.findAll();
    }

    public List<Investment> searchInvestments(Map<String, String> criteria) {
        // 实现根据条件筛选的逻辑
        return investmentDao.search(criteria);
    }

    public Investment getInvestmentById(Long id) {
        return investmentDao.findById(id);
    }

    public List<InvestmentRecord> getInvestmentRecords(Long id) {
        return investmentDao.findRecordsByInvestmentId(id);
    }
}
