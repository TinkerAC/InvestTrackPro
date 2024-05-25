<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>交易</title>
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
            max-width: 400px;
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
</head>
<body>
<div class="container">
    <h1>交易页面</h1>
    <span class="message">${requestScope.message}</span>
    <c:if test="${not empty requestScope.investment}">
        <p>当前交易信息</p>
        <p>投资id：${requestScope.investment.investmentId}</p>
        <p>当前市值：${requestScope.investment.currentValue}</p>
        <form action="${pageContext.request.contextPath}/asset/buy" method="post">
            <label>买入价格：${requestScope.investment.currentValue}</label>
            <label for="amount">买入数量：</label>
            <input type="number" id="amount" name="amount" min="0.01" step="0.01" required>
            <input type="hidden" name="investmentId" value="${requestScope.investment.investmentId}">
            <input type="submit" value="提交">
        </form>
    </c:if>
</div>
</body>
</html>
<jsp:include page="footer.jsp"/>