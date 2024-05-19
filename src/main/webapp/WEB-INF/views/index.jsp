<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<h2>Click the following buttons to register or login</h2>
<button onclick="location.href='user/register'">Register</button>
<button onclick="location.href='user/login'">Login</button>
<button onclick="location.href='investment'">查看投资项目</button>
</body>
</html>