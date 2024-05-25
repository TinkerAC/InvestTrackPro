<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
<h1>用户列表</h1>
<table border='1'>
    <tr>
        <th>用户ID</th>
        <th>用户名</th>
        <th>密码</th>
        <th>邮箱</th>
        <th>电话</th>
        <th>角色</th>
        <th>创建时间</th>
        <th>更新时间</th>

    </tr>
    <c:forEach var="user" items="${users}">
    <tr>
        <td>${user.userId}</td>
        <td>${user.username}</td>
        <td>${user.password}</td>
        <td>${user.email}</td>
        <td>${user.phone}</td>
        <td>${user.role}</td>
        <td>${user.createdAt}</td>
        <td>${user.updatedAt}</td>
    </tr>
    </c:forEach>

</body>
</html>
