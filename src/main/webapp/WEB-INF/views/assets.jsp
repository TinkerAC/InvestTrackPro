<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>我的资产</title>
</head>
<body>
<h1>我的资产</h1>

<table border="1">
    <tr>
        <th>资产id</th>
        <th>投资项目id</th>
        <th>投资项目名</th>
        <th>投资项目分类</th>
        <th>持有数量</th>

    </tr>
    <c:forEach items="${assets}" var="asset">
    <tr>
        <td>${asset.assetId}</td>
        <td>${asset.investmentId}</td>
        <td>${requestScope.investmentMap.get(asset.investmentId).name}</td>
        <td>${requestScope.investmentMap.get(asset.investmentId).category}</td>
        <td>${asset.amount}</td>


    </tr>
    </c:forEach>
</body>
</html>
