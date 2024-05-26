<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投资详情页</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body class="bg-blue-100 min-h-screen flex flex-col items-center justify-center">
<div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-2xl">
    <div class="text-center mb-6">
        <h1 class="text-2xl font-semibold text-gray-700">投资详情</h1>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <h2 class="text-lg font-medium text-gray-700 mb-4">欢迎, <c:out value="${sessionScope.user.username}"/></h2>

            <c:choose>
                <c:when test="${not empty requestScope.investment}">
                    <div class="mb-4">
                        <h3 class="text-lg text-gray-700"><strong>投资名称：</strong> <c:out value="${requestScope.investment.name}"/></h3>
                        <h3 class="text-lg text-gray-700"><strong>初始市值：</strong> <c:out value="${requestScope.investment.initialValue}"/></h3>
                        <h3 class="text-lg text-gray-700"><strong>当前市值：</strong> <c:out value="${requestScope.investment.currentValue}"/></h3>
                    </div>
                    <div class="mb-6">
                        <canvas id="investmentChart" width="400" height="200"></canvas>
                    </div>
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/asset/buy?investmentId=${requestScope.investment.investmentId}" class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50">交易</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:redirect url="${pageContext.request.contextPath}/404"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <h2 class="text-xl font-medium text-red-600">您尚未登录</h2>
            <div class="text-center mt-6">
                <a href="${pageContext.request.contextPath}/user/login" class="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">去登录</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Tailwind CSS JS -->
<script src="https://cdn.tailwindcss.com"></script>
<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const dates = <c:out value="${requestScope.dates}" escapeXml="false"/>;
    const values = <c:out value="${requestScope.values}" escapeXml="false"/>;
    const ctx = document.getElementById('investmentChart').getContext('2d');
    const investmentChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets: [{
                label: '收盘价',
                data: values,
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 2,
                fill: false

            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: false,
                    ticks: {
                        stepSize: 0.2
                    }
                }
            }
        }
    });
</script>
<jsp:include page="footer.jsp"/>
</body>
</html>
