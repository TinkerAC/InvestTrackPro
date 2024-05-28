<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>数据分析报告</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-gray-100 text-gray-900">

<div class="container mx-auto p-4">
    <h1 class="text-3xl font-bold mb-4">数据分析报告</h1>

    <section class="mb-8">
        <h2 class="text-2xl font-semibold mb-2">每日投资变化</h2>
        <table class="table-auto w-full mb-4">
            <thead>
            <tr class="bg-gray-200">
                <th class="px-4 py-2">投资项目</th>
                <th class="px-4 py-2">开盘价</th>
                <th class="px-4 py-2">收盘价</th>
                <th class="px-4 py-2">最高价</th>
                <th class="px-4 py-2">最低价</th>
                <th class="px-4 py-2">成交量</th>
                <th class="px-4 py-2">涨跌幅</th>
                <th class="px-4 py-2">涨跌价格</th>
                <th class="px-4 py-2">日期</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="change" items="${investmentDailyChanges}">
                <tr>
                    <td class="border px-4 py-2">
                        <c:forEach var="investment" items="${investments}">
                            <c:if test="${investment.investmentId == change.investmentId}">
                                ${investment.name}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.openingValue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.closingValue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.highValue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.lowValue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.volume}" type="number"
                                                                   maxFractionDigits="0"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.changePercent}" type="number"
                                                                   maxFractionDigits="2"/>%
                    </td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${change.changeValue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatDate value="${change.date}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>

    <section class="mb-8">
        <h2 class="text-2xl font-semibold mb-2">资产信息</h2>
        <table class="table-auto w-full mb-4">
            <thead>
            <tr class="bg-gray-200">
                <th class="px-4 py-2">资产ID</th>
                <th class="px-4 py-2">投资项目ID</th>
                <th class="px-4 py-2">持有数量</th>
                <th class="px-4 py-2">持有收益</th>
                <th class="px-4 py-2">实现收益</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="asset" items="${assets}">
                <tr>
                    <td class="border px-4 py-2">${asset.assetId}</td>
                    <td class="border px-4 py-2">${asset.investmentId}</td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${asset.amount}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${asset.holdingProfit}" type="number"
                                                                   maxFractionDigits="2"/></td>
                    <td class="border px-4 py-2"><fmt:formatNumber value="${asset.totalSellRevenue}" type="number"
                                                                   maxFractionDigits="2"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>

    <section class="mb-8">
        <h2 class="text-2xl font-semibold mb-2">资产总额</h2>
        <p class="bg-white p-4 shadow rounded mb-4">
            用户总资产: <fmt:formatNumber value="${requestScope.userTotalAssetValue}" type="number"
                                          maxFractionDigits="2"/>元
        </p>
    </section>

    <section class="mb-8 bg-white p-4 shadow rounded">
        <div class="mb-4 flex justify-center">
            <canvas id="myPieChart" class="w-1/2 max-w-sm"></canvas>
        </div>
        <script>
            const ctxPie = document.getElementById('myPieChart').getContext('2d');
            new Chart(ctxPie, {
                type: 'pie',
                data: {
                    labels: ${requestScope.labelsForPieChart},
                    datasets: [{
                        label: '资产分布',
                        data: ${requestScope.dataForPieChart},
                        backgroundColor: ['rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)', 'rgba(255, 159, 64, 0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)'],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    return `${context.label || ''}: ${context.parsed != null ? context.parsed : ''}`;
                                }
                            }
                        }
                    }
                }
            });
        </script>
    </section>


    <section class="mb-8">
        <h2 class="text-2xl font-semibold mb-2">用户收益分析</h2>
        <p class="bg-white p-4 shadow rounded mb-4">
            总持有收益:
            <c:set var="totalHoldingProfit" value="${0}" scope="page"/>
            <c:forEach var="asset" items="${assets}">
                <c:set var="totalHoldingProfit" value="${totalHoldingProfit + asset.holdingProfit}" scope="page"/>
            </c:forEach>
            <fmt:formatNumber value="${totalHoldingProfit}" type="number" maxFractionDigits="2"/>元
        </p>
        <p class="bg-white p-4 shadow rounded mb-4">
            总实现收益:
            <c:set var="sellRevenue" value="${0}" scope="page"/>
            <c:forEach var="asset" items="${requestScope.assets}">
                <c:set var="sellRevenue" value="${sellRevenue + asset.totalSellRevenue}" scope="page"/>
            </c:forEach>
            <fmt:formatNumber value="${sellRevenue}" type="number" maxFractionDigits="2"/>元</p>

    </section>

    <section class="mb-8 bg-white p-4 shadow rounded">
        <div class="mb-4 flex justify-center">
            <canvas id="roiChart" width="400" height="200"></canvas>
        </div>
        <script>
            // 从JSP传递的变量中获取用户ROI和所有用户的ROI列表
            const userROI = ${requestScope.userROI};
            const allROIs = ${requestScope.ROIs};

            // 获取用户ROI在列表中的排名
            const sortedROIs = [...allROIs].sort((a, b) => b - a); // 降序排列
            const userRank = sortedROIs.indexOf(userROI) + 1;

            // 准备图表数据
            const labels = sortedROIs.map((roi, index) => `用户X`);
            const data = {
                labels: labels,
                datasets: [{
                    label: '用户收益率',
                    data: sortedROIs,
                    backgroundColor: sortedROIs.map(roi => roi === userROI ? 'rgba(75, 192, 192, 0.2)' : 'rgba(153, 102, 255, 0.2)'),
                    borderColor: sortedROIs.map(roi => roi === userROI ? 'rgba(75, 192, 192, 1)' : 'rgba(153, 102, 255, 1)'),
                    borderWidth: 1
                }]
            };

            // 配置图表选项
            const config = {
                type: 'bar',
                data: data,
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    },
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    let label = context.dataset.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += context.parsed.y;
                                    if (context.parsed.y === userROI) {
                                        label += ' (当前用户)';
                                    }
                                    return label;
                                }
                            }
                        }
                    }
                }
            };

            // 渲染图表
            const myChart = new Chart(
                document.getElementById('roiChart'),
                config
            );
        </script>
    </section>


    <section class="mb-8">
        <h2 class="text-2xl font-semibold mb-2">用户收益率排名</h2>
        <div>
            <p class="bg-white p-4 shadow rounded mb-4">
                当前用户收益率: <fmt:formatNumber value="${userROI}" type="number" maxFractionDigits="2"/>%
            </p>
            <p class="bg-white p-4 shadow rounded mb-4">
                您跑赢了 <fmt:formatNumber value="${userRank}" type="number"/>% 的财友!
            </p>
        </div>
    </section>


</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
