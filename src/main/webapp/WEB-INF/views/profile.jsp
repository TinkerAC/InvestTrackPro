<%@ page import="com.zufe.cpy.investtrackpro.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人资料</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="card">
        <div class="card-header text-center">
            <h1>个人资料</h1>
        </div>
        <div class="card-body">
            <%
                User user = (User) request.getSession().getAttribute("user");

                if (user != null) {
            %>
            <h2 class="text-center">您好，<%= user.getUsername() %></h2>
            <p class="text-center">您的邮箱是：<%= user.getEmail() %></p>
            <%
            } else {
            %>
            <h2 class="text-center text-danger">您尚未登录</h2>
            <%
                }
            %>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
