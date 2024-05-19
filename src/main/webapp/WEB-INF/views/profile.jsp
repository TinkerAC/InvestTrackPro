<%@ page import="com.zufe.cpy.investtrackpro.model.User" %><%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/18
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人资料</title>
</head>
<body>
<h1>个人资料</h1>
<%
    User user = (User) request.getSession().getAttribute("user");

    if (user != null) {
        out.println("<h2>您好，" + user.getUsername() + "</h2>");
        out.println("<h2>您的邮箱是：" + user.getEmail() + "</h2>");

    } else {
        out.println("<h2>您尚未登录</h2>");
    }
%>
</body>
</html>
