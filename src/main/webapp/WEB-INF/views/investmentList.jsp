<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2024/5/20
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<table border='1'>
    <tr>
        <th>投资ID</th>
        <th>投资名称</th>
        <th>初始市值</th>
        <th>当前市值</th>
        <th>操作</th>
    </tr>
    <c:forEach var="investment" items="${investments}">
        <tr>
            <td>${investment.investmentId}</td>
            <td>${investment.name}</td>
            <td>${investment.initialValue}</td>
            <td>${investment.currentValue}</td>
            <td><a href='investment/details?id=${investment.investmentId}'>查看详情</a></td>
        </tr>
    </c:forEach>
</table>