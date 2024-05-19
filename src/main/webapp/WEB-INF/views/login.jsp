<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/18
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>InvestTrack Pro-登录页</title>
</head>
<body>
<h1>登录页</h1>
<%
    String e = (String) request.getAttribute("error");
    if (e != null) {
        if (!e.isEmpty()) {
            out.println("<font color='red'>" + e + "</font>");
        }
    }
%>
<form action="login" method="post">
    <label for="email">邮箱:</label>
    <input type="text" id="email" name="email" required>
    <br>
    <label for="password">密码:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <input type="submit" value="登录">
</form>
<h1>没有账号？<a href="register">注册</a></h1>
</body>
</html>
