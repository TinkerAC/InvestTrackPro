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
            <th>投资项目ID</th>
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
                <td>${change.investmentId}</td>
                <td>${change.openingValue}</td>
                <td>${change.closingValue}</td>
                <td>${change.highestValue}</td>
                <td>${change.lowestValue}</td>
                <td>${change.volume}</td>
                <td>${change.changePercent}%</td>
                <td>${change.changeValue}</td>
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
        </tr>
        </thead>
        <tbody>
        <c:forEach var="asset" items="${assets}">
            <tr>
                <td>${asset.assetId}</td>
                <td>${asset.investmentId}</td>
                <td>${asset.amount}</td>
                <%
                    List<InvestmentRecord> investmentRecords = (List<InvestmentRecord>) request.getAttribute("investmentRecords");
                    double totalBuyCost = 0;
                    double totalSellIncome = 0;


                    // 遍历交易记录
                    for (InvestmentRecord investmentRecord : investmentRecords) {
                        if (investmentRecord.getOperation().equals("buy")) {
                            totalBuyCost += investmentRecord.getAmount() * investmentRecord.getCurrentPrize();
                            currentHoldQuantity += investmentRecord.getAmount();
                        } else if (investmentRecord.getOperation().equals("sell")) {
                            totalSellIncome += investmentRecord.getAmount() * investmentRecord.getCurrentPrize();
                            currentHoldQuantity -= investmentRecord.getAmount();
                        }
                    }

                    // 计算当前持有部分的价值
                    double currentHoldValue = asset.currentPrize * asset.amount;

                    // 计算持有收益
                    double holdingProfit = currentHoldValue + totalSellIncome - totalBuyCost;
                    request.setAttribute("holdingProfit", holdingProfit);
                %>
                <td>${holdingProfit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>持有收益分析</h2>
    <table>
        <thead>
        <tr>
            <th>InvestmentId</th>
            <th>Amount</th>
            <th>Initial Value</th>
            <th>Current Value</th>
            <th>Change Value</th>
            <th>Change Percent</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="asset" items="${assets}">
            <c:set var="initialValue" value="0"/>
            <c:set var="currentValue" value="0"/>
            <c:forEach var="change" items="${investmentDailyChanges}">
                <c:if test="${change.investmentId == asset.investmentId}">
                    <c:set var="initialValue" value="${change.openingValue}" scope="page"/>
                    <c:set var="currentValue" value="${change.closingValue}" scope="page"/>
                </c:if>
            </c:forEach>
            <c:set var="changeValue" value="${(currentValue - initialValue) * asset.amount}" scope="page"/>
            <c:set var="changePercent" value="${((currentValue - initialValue) / initialValue) * 100}" scope="page"/>
            <tr>
                <td>${asset.investmentId}</td>
                <td>${asset.amount}</td>
                <td>${initialValue}</td>
                <td>${currentValue}</td>
                <td>${changeValue}</td>
                <td>${changePercent}%</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>每日投资变化图表</h2>
    <canvas id="investmentChart" width="400" height="200"></canvas>
    <script>
        var ctx = document.getElementById('investmentChart').getContext('2d');
        var investmentData = {
            labels: [
                <c:forEach var="change" items="${investmentDailyChanges}">
                '<fmt:formatDate value="${change.date}" pattern="yyyy-MM-dd"/>',
                </c:forEach>
            ],
            datasets: [{
                label: 'Closing Value',
                data: [
                    <c:forEach var="change" items="${investmentDailyChanges}">
                    ${change.closingValue},
                    </c:forEach>
                ],
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                fill: false,
                borderWidth: 1
            }]
        };
        var investmentChart = new Chart(ctx, {
            type: 'line',
            data: investmentData,
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: '每日投资变化'
                },
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            unit: 'day',
                            tooltipFormat: 'll',
                            displayFormats: {
                                'day': 'MMM D'
                            }
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Date'
                        }
                    }],
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Value'
                        }
                    }]
                }
            }
        });
    </script>

    <h2>资产总额</h2>
    <p class="total-asset">
        用户总资产: ${totalAsset}
    </p>
</div>
</body>
</html>
