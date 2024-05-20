<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2024/5/20
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tbody>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${investments}" var="investment">
    <tr>
        <td>${investment.investmentId}</td>
        <td>${investment.name}</td>
        <td>${investment.initialValue}</td>
        <td>${investment.currentValue}</td>
        <td><a href="investment/details?id=${investment.investmentId}">查看详情</a></td>
    </tr>
</c:forEach>
</tbody>