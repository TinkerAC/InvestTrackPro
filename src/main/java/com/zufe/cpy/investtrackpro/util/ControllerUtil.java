package com.zufe.cpy.investtrackpro.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.zufe.cpy.investtrackpro.controller.IndexController;
import com.zufe.cpy.investtrackpro.dao.InvestmentDailyChangeDao;
import com.zufe.cpy.investtrackpro.dao.InvestmentDao;
import com.zufe.cpy.investtrackpro.model.Investment;
import com.zufe.cpy.investtrackpro.model.InvestmentDailyChange;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ControllerUtil {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static void directToNotFoundPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/notFound");

    }
}
