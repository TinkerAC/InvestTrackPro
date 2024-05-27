<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>交易</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-color: #f4f4f4;
        }

        h1 {
            color: #333;
        }

        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px; /* 加宽容器 */
            box-sizing: border-box;
        }

        .message {
            color: red;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin: 10px 0 5px;
            color: #333;
        }

        input[type="number"],
        input[type="submit"] {
            padding: 10px;
            margin: 5px 0 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="radio"] {
            margin: 0 5px 10px 0;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function updateFormAction() {
            var transactionType = document.querySelector('input[name="transactionType"]:checked').value;
            var form = document.getElementById('transactionForm');
            if (transactionType === 'buy') {
                form.action = '${pageContext.request.contextPath}/asset/buy';
            } else {
                form.action = '${pageContext.request.contextPath}/asset/sell';
            }
        }

        function validateForm() {
            var amount = document.getElementById('amount').value;
            if (amount <= 0) {
                alert('请输入有效的数量');
                return false;
            }
            if (document.getElementById('sell').checked && amount > ${requestScope.asset.amount}) {
                alert('卖出数量不能大于持有数量');
                return false;
            }
            return true;
        }
    </script>
</head>
<body class="bg-gray-50">
<header class="mb-8 w-full">
    <nav class="bg-white shadow p-6 rounded-lg flex justify-between items-center w-full max-w-6xl mx-auto">
        <h1 class="text-2xl font-bold text-gray-700">我的资产管理</h1>
        <div>
            <!-- Personal Home Page Link -->
            <a href="<%=request.getContextPath()%>/user/profile"
               class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
                个人主页
            </a>

            <!-- Logout Link -->
            <a href="<%=request.getContextPath()%>/user/logout"
               class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 ml-4">
                登出
            </a>
        </div>
    </nav>
</header>
<main class="flex-grow w-full ">
    <div class="container mx-auto">
        <h1 class="text-center">交易页面</h1>
        <span class="message">${requestScope.message}</span>
        <c:if test="${not empty requestScope.investment}">
            <div class="mb-4">
                <p>当前交易信息</p>
                <p>投资名称：${requestScope.investment.name}</p>
                <p>当前市值：${requestScope.investment.currentValue}</p>
                <c:if test="${not empty requestScope.asset}">
                    <p>持有数量: ${requestScope.asset.amount}</p>
                </c:if>
                <c:if test="${empty requestScope.asset}">
                    <p>持有数量: 0</p>
                </c:if>
            </div>
            <form id="transactionForm" method="post" onsubmit="updateFormAction()">
                <label>交易类型：</label>
                <div class="flex items-center mb-4">
                    <input type="radio" id="buy" name="transactionType" value="buy" checked class="mr-2">
                    <label for="buy" class="mr-4">买入</label>
                    <input type="radio" id="sell" name="transactionType" value="sell" class="mr-2">
                    <label for="sell">卖出</label>
                </div>

                <label for="amount">数量：</label>
                <input type="number" id="amount" name="amount" min="0.01" step="0.01" required>

                <input type="hidden" name="investmentId" value="${requestScope.investment.investmentId}">
                <input type="submit" value="提交" class="mt-4">
            </form>
        </c:if>
    </div>
</main>
<footer class="mt-8 w-full">
    <div class="text-center text-gray-600 py-4 bg-white shadow">
        &copy; 2024 InvestTrackPro. 保留所有权利.
    </div>
</footer>
</body>
</html>
