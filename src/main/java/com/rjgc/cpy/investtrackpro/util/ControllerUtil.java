package com.rjgc.cpy.investtrackpro.util;

import java.io.IOException;

import com.rjgc.cpy.investtrackpro.controller.IndexController;
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
