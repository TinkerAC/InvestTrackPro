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
    <title>InvestTrack Pro-注册页</title>
</head>
<body>
<h1>注册页</h1>
<%
    String e = (String) request.getAttribute("error");
    if (e != null) {
        if (!e.isEmpty()) {
            out.println("<font color='red'>" + e + "</font>");
        }
    }
%>

<form action="register" method="post">
    <label for="username">用户名:</label>
    <input type="text" id="username" name="username" required>
    <br>
    <label for="password">密码:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <label for="email">邮箱:</label>
    <input type="email" id="email" name="email" required>
    <br>
    <input type="submit" value="注册">
</form>
<h1>已有账号？<a href="login">登录</a></h1>
</body>
</html>
