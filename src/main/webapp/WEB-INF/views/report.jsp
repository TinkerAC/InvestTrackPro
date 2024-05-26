<%@ page import="com.zufe.cpy.investtrackpro.model.InvestmentRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zufe.cpy.investtrackpro.model.Investment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>数据分析报告</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="css/styles.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f9;
            color: #333;
        }

        h1, h2 {
            color: #2c3e50;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #34495e;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        canvas {
            display: block;
            margin: 0 auto;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .total-asset {
            font-size: 1.2em;
            font-weight: bold;
            text-align: center;
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>数据分析报告</h1>

    <h2>每日投资变化</h2>
    <table>
        <thead>
        <tr>
            <th>投资项目</th>
            <th>开盘价</th>
            <th>收盘价</th>
            <th>最高价</th>
            <th>最低价</th>
            <th>成交量</th>
            <th>涨跌幅</th>
            <th>涨跌价格</th>
            <th>日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="change" items="${investmentDailyChanges}">
            <tr>
                <td>
                    <c:forEach var="investment" items="${investments}">
                        <c:if test="${investment.investmentId == change.investmentId}">
                            ${investment.name}
                        </c:if>
                    </c:forEach>
                </td>
                <td><fmt:formatNumber value="${change.openingValue}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${change.closingValue}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${change.highValue}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${change.lowValue}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${change.volume}" type="number" maxFractionDigits="0"/></td>
                <td><fmt:formatNumber value="${change.changePercent}" type="number" maxFractionDigits="2"/>%</td>
                <td><fmt:formatNumber value="${change.changeValue}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatDate value="${change.date}" pattern="yyyy-MM-dd"/></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>资产信息</h2>
    <table>
        <thead>
        <tr>
            <th>资产ID</th>
            <th>投资项目ID</th>
            <th>持有数量</th>
            <th>持有收益</th>
            <th>实现收益</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="asset" items="${assets}">
            <tr>
                <td>${asset.assetId}</td>
                <td>${asset.investmentId}</td>
                <td><fmt:formatNumber value="${asset.amount}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${asset.holdingProfit}" type="number" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${asset.totalSellRevenue}" type="number" maxFractionDigits="2"/></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>


    <h2>资产总额</h2>
    <c:set var="totalAsset" value="${0}" scope="page"/>
    <p class="total-asset">
        用户总资产:
        <c:forEach var="investmentRecord" items="${investmentRecords}">
            <c:choose>
                <c:when test="${investmentRecord.operation == '买入'}">
                    <c:set var="totalAsset"
                           value="${totalAsset + investmentRecord.amount * investmentRecord.currentPrize}"
                           scope="page"/>
                </c:when>
                <c:when test="${investmentRecord.operation == '卖出'}">
                    <c:set var="totalAsset"
                           value="${totalAsset - investmentRecord.amount * investmentRecord.currentPrize}"
                           scope="page"/>
                </c:when>
            </c:choose>
        </c:forEach>
        ${totalAsset}元
    </p>

</div>
</body>
</html>
<jsp:include page="footer.jsp"/>