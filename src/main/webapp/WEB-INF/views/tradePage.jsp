<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>交易</title>
</head>
<body>

<h1>交易页面</h1>

<c:if test="${not empty requestScope.investment}">
    <p>当前交易信息</p>
    <p>投资id：${requestScope.investment.investmentId}</p>
    <p>当前市值：${requestScope.investment.currentValue}</p>
    <form action=${pageContext.request.contextPath}/asset/buy method="post">
        <label>买入价格：${requestScope.investment.currentValue}</label>
        <label for="amount">买入数量：</label>
        <input type="number" id="amount" name="amount" min="0.01" step="0.01" required>
        <input type="hidden" name="investmentId" value="${requestScope.investment.investmentId}">
        <input type="submit" value="提交">
    </form>
</c:if>

<c:if test="${empty requestScope.investment}">
    <p>没有交易信息。</p>
</c:if>

</body>
</html>
