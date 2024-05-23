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
        <th>资产编号</th>
        <th>资产名称</th>
        <th>资产类型</th>
        <th>资产数量</th>
        <th>资产单位</th>
        <th>资产价格</th>
        <th>资产总价</th>
    </tr>
    <c:forEach items="${requestScope.assets}" var="asset">
        <tr>
            <td>${asset.id}</td>
            <td>${asset.name}</td>
            <td>${asset.type}</td>
            <td>${asset.number}</td>
            <td>${asset.unit}</td>
            <td>${asset.price}</td>
            <td>${asset.totalPrice}</td>
        </tr>
    </c:forEach>

</body>
</html>
