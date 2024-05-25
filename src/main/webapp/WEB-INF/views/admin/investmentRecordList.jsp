<%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/25
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>InvestmentRecordList</title>
</head>
<body>
<h1>Investment Record List</h1>
<table border='1'>
    <tr>
        <th>投资记录ID</th>
        <th>投资ID</th>
        <th>交易份额</th>
        <th>当前市值</th>
        <th>操作</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>更新时间</th>

    </tr>
    <c:forEach var="record" items="${investmentRecords}">
    <tr>
        <td>${record.investmentRecordId}</td>
        <td>${record.investmentId}</td>
        <td>${record.amount}</td>
        <td>${record.currentPrize}</td>
        <td>${record.operation}</td>
        <td>${record.status}</td>
        <td>${record.createdAt}</td>
        <td>${record.updatedAt}</td>

    </tr>
    </c:forEach>


</body>
</html>
