<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>投资管理系统</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&amp;display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .positive-change {
            color: red;
        }

        .negative-change {
            color: green;
        }
    </style>

    <script>

        //前端搜索
        $(document).ready(function () {
            $('#searchButton').on('click', function (event) {
                event.preventDefault(); // 阻止表单提交

                var category = $('#category').val().toLowerCase();
                var riskLevel = $('#riskLevel').val().toLowerCase();

                $('#investmentTable tbody tr').each(function () {
                    var matchesCategory = !category || $(this).find('td:eq(1)').text().toLowerCase().includes(category);
                    var matchesRiskLevel = !riskLevel || $(this).find('td:eq(2)').text().toLowerCase().includes(riskLevel);

                    $(this).toggle(matchesCategory && matchesRiskLevel);
                });
            });
        });

    </script>
</head>
<body class="bg-blue-100">
<div class="min-h-screen flex items-center justify-center">
    <div class="bg-white p-10 rounded-lg shadow-xl w-full max-w-2xl">
        <h1 class="text-2xl font-semibold text-center text-gray-700 mb-6">投资列表</h1>
        <div>
            <form action="investment/search" method="GET" class="mb-6">
                <div class="mb-4">
                    <label for="category" class="block text-gray-700 text-sm font-semibold mb-2">类型:</label>
                    <select name="category" id="category"
                            class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
                        <option value="">不限</option>
                        <option value="股票">股票</option>
                        <option value="债券">债券</option>
                        <option value="基金">基金</option>
                        <option value="房地产">房地产</option>
                        <option value="商品">大宗商品</option>
                    </select>
                </div>

                <div class="mb-4">
                    <label for="riskLevel" class="block text-gray-700 text-sm font-semibold mb-2">风险等级:</label>
                    <select name="riskLevel" id="riskLevel"
                            class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
                        <option value="">不限</option>
                        <option value="1">低风险</option>
                        <option value="2">中低风险</option>
                        <option value="3">中风险</option>
                        <option value="4">中高风险</option>
                        <option value="5">高风险</option>
                    </select>
                </div>

                <button id="searchButton"
                        type="submit"
                        class="w-full bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">
                    搜索
                </button>
            </form>
        </div>
        <!-- 如果有投资信息，则显示表格 -->
        <c:if test="${not empty investments}">
            <table id="investmentTable" class="w-full border-collapse border border-gray-300">
                <thead>
                <tr class="bg-gray-100">
                    <th class="border border-gray-300 px-4 py-2">投资ID</th>
                    <th class="border border-gray-300 px-4 py-2">投资名称</th>
                    <th class="border border-gray-300 px-4 py-2">当前市值</th>
                    <th class="border border-gray-300 px-4 py-2">涨跌</th>
                    <th class="border border-gray-300 px-4 py-2">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="investment" items="${investments}">
                    <c:set var="changePercent"
                           value="${requestScope.investmentDailyChangeMap.get(investment.investmentId).changePercent}"/>
                    <c:set var="changeColor" value="${changePercent > 0 ? 'red' : 'green'}"/>
                    <tr>
                        <td class="border border-gray-300 px-4 py-2">${investment.investmentId}</td>
                        <td class="border border-gray-300 px-4 py-2">${investment.name}</td>
                        <td class="border border-gray-300 px-4 py-2">
                            <fmt:formatNumber value="${investment.currentValue}" type="number" minFractionDigits="2"
                                              maxFractionDigits="2"/>
                        </td>
                        <c:if test="${changePercent > 0}">
                            <td class="border border-gray-300 px-4 py-2" style="color: red">
                                +<fmt:formatNumber value="${changePercent}" type="number" minFractionDigits="2"
                                                   maxFractionDigits="2"/>%
                            </td>
                        </c:if>
                        <c:if test="${changePercent < 0}">
                            <td class="border border-gray-300 px-4 py-2" style="color: green">
                                <fmt:formatNumber value="${changePercent}" type="number" minFractionDigits="2"
                                                  maxFractionDigits="2"/>%
                            </td>
                        </c:if>
                        <td class="border border-gray-300 px-4 py-2">
                            <a href='investment/details?id=${investment.investmentId}'
                               class="text-indigo-600 hover:underline">查看详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>


        <!-- 如果没有投资信息，显示提示消息 -->
        <c:if test="${empty investments}">
            <div class="message text-center text-gray-500 mt-6">没有找到投资信息。</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
