<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
    <title>我的资产</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #444;
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            color: #555;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
<h1>我的资产</h1>

<table>
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
</table>
<button onclick="window.location.href='report'">查看报告</button>
</body>
</html>
