<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.rjgc.cpy.investtrackpro.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员页面</title>
    <!-- Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>    .centered-heading {
        text-align: center;
    }
    </style>

</head>

<body class="bg-gray-100">
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-2xl mb-4">
    <div class="container">
        <a class="navbar-brand text-2xl font-semibold text-gray-600" href="#">管理员界面</a>
        <button class="bg-white hover:bg-gray-700 text-white font-bold py-2 px-4 rounded">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link btn btn-primary" aria-current="page" href="${pageContext.request.contextPath}/admin/resetSystem">重置系统</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-primary" href="${pageContext.request.contextPath}/admin/logout">退出登录</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
            <div class="card shadow-xl">
                <div class="card-body">
                    <h1 class="card-title text-2xl font-semibold text-gray-700 centered-heading">管理者系统</h1>
                    <h2 class="card-title text-2xl font-semibold text-gray-700 centered-heading">欢迎您，${sessionScope.user.username}</h2>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>邮箱:</strong> ${sessionScope.user.email}</li>
                        <li class="list-group-item"><strong>您的身份是：</strong> ${sessionScope.user.role}</li>
                    </ul>

                </div>
                <div class="card-footer d-grid gap-2">
                    <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-default">查看用户列表</a>
                    <a href="${pageContext.request.contextPath}/investment" class="btn btn-default">查看投资列表</a>
                    <a href="${pageContext.request.contextPath}/admin/investmentRecord" class="btn btn-white">查看交易记录</a>
                    <a href="${pageContext.request.contextPath}/asset/view" class="btn btn-info">查看资产</a>
                    <a href="${pageContext.request.contextPath}/admin/randomUser" class="btn btn-success">新增5位随机用户</a>
                    <a href="${pageContext.request.contextPath}/admin/randomBuy" class="btn btn-warning">所有用户随机购买投资</a>
                    <a href="${pageContext.request.contextPath}/admin/randomSell" class="btn btn-warning">所有用户随机出售资产</a>
                    <a href="${pageContext.request.contextPath}/admin/madeInHaven" class="btn btn-danger">「Made In Haven!」</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
