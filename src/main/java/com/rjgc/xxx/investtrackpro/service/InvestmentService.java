package com.rjgc.xxx.investtrackpro.service;

import com.rjgc.xxx.investtrackpro.dao.InvestmentRecordDao;
import com.rjgc.xxx.investtrackpro.dao.InvestmentDao;
import com.rjgc.xxx.investtrackpro.model.Investment;
import com.rjgc.xxx.investtrackpro.model.InvestmentDailyChange;
import com.rjgc.xxx.investtrackpro.model.InvestmentRecord;
import com.rjgc.xxx.investtrackpro.dao.InvestmentDailyChangeDao;

import java.util.List;
import java.util.Map;

public class InvestmentService {

    private final InvestmentDao investmentDao;
    private final InvestmentRecordDao investmentRecordDao;
    private final InvestmentDailyChangeDao investmentDailyChangeDao = new InvestmentDailyChangeDao();

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

    public InvestmentDailyChange getLatestInvestmentDailyChanges(int investmentId) {
        return investmentDailyChangeDao.findLatestByInvestmentId(investmentId);
    }
    public List<InvestmentDailyChange> getInvestmentDailyChanges(int investmentId) {
        return investmentDailyChangeDao.findByInvestmentId(investmentId);
    }
    public List<InvestmentDailyChange> getInvestmentDailyChanges(List<Integer> investmentIds) {
        return investmentDailyChangeDao.findLatestByInvestmentId(investmentIds);
    }
}
