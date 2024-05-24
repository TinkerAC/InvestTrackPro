
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>InvestTrack Pro-登录页</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #f5f5f5, #d6e0f0);
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }
        h1 {
            color: #444;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-top: 20px;
            font-weight: bold;
            text-align: left;
        }
        input[type="text"], input[type="password"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: calc(100% - 22px);
            margin-top: 8px;
        }
        .btn {
            margin-top: 20px;
            padding: 12px;
            border: none;
            border-radius: 5px;
            width: 100%;
            font-size: 16px;
            cursor: pointer;
            background: #4CAF50;
            color: white;
            transition: background 0.3s;
        }
        .btn:hover {
            background: #45a049;
        }
        .error {
            color: red;
            margin-top: 20px;
        }
        .register {
            margin-top: 30px;
            color: #444;
        }
        .register a {
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s;
        }
        .register a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>登录页</h1>
    <%
        String e = (String) request.getAttribute("error");
        if (e != null && !e.isEmpty()) {
    %>
    <div class="error"><%= e %></div>
    <%
        }
    %>
    <form action="login" method="post">
        <label for="email">邮箱:</label>
        <input type="text" id="email" name="email" required>
        <label for="password">密码:</label>
        <input type="password" id="password" name="password" required>
        <button type="submit" class="btn">登录</button>
    </form>
    <div class="register">
        <h1>没有账号？<a href="register">注册</a></h1>
    </div>
</div>
</body>
</html>
