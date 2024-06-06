<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.rjgc.cpy.investtrackpro.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员页面</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .centered-heading {
            text-align: center;
        }

        .btn-red {
            background-color: #ff0000;
            color: white;
        }

        .btn-red:hover {
            background-color: #cc0000;
        }

        .btn-orange {
            background-color: #ff7f00;
            color: white;
        }

        .btn-orange:hover {
            background-color: #cc6600;
        }

        .btn-yellow {
            background-color: #ffff00;
            color: black;
        }

        .btn-yellow:hover {
            background-color: #cccc00;
        }

        .btn-green {
            background-color: #00ff00;
            color: white;
        }

        .btn-green:hover {
            background-color: #00cc00;
        }

        .btn-blue {
            background-color: #0000ff;
            color: white;
        }

        .btn-blue:hover {
            background-color: #0000cc;
        }

        .btn-indigo {
            background-color: #4b0082;
            color: white;
        }

        .btn-indigo:hover {
            background-color: #380061;
        }

        .btn-purple {
            background-color: #800080;
            color: white;
        }

        .btn-purple:hover {
            background-color: #660066;
        }
    </style>
</head>

<body class="bg-gray-100">
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow mb-4">
    <div class="container">
        <a class="navbar-brand text-2xl font-semibold text-gray-600" href="#">管理员界面</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link btn btn-primary text-white" aria-current="page"
                       href="<c:url value='/admin/resetSystem'/>">重置系统</a>
                </li>
                <li>

                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-primary text-white" href="<c:url value='/admin/logout'/>">退出登录</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
            <div class="card shadow">
                <div class="card-body">
                    <h1 class="card-title text-2xl font-semibold text-gray-700 centered-heading">管理员系统</h1>
                    <h2 class="card-title text-2xl font-semibold text-gray-700 centered-heading">
                        欢迎您，${sessionScope.user.username}</h2>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>邮箱:</strong> ${sessionScope.user.email}</li>
                        <li class="list-group-item"><strong>您的身份是：</strong> ${sessionScope.user.role}</li>
                        <li class="list-group-item" style="color:red"><strong>消息:</strong> ${requestScope.message}</li>
                    </ul>
                </div>
                <div class="card-footer d-grid gap-2">
                    <a href="<c:url value='/admin/user'/>" class="btn btn-red">查看用户列表</a>
                    <a href="<c:url value='/investment'/>" class="btn btn-orange">查看投资列表</a>
                    <a href="<c:url value='/admin/investmentRecord'/>" class="btn btn-yellow">查看交易记录</a>
                    <a href="<c:url value='/asset/view'/>" class="btn btn-green">查看资产</a>
                    <a href="<c:url value='/admin/randomUser'/>" class="btn btn-blue">新增5位随机用户</a>
                    <a href="<c:url value='/admin/randomBuy'/>" class="btn btn-indigo">所有用户随机购买投资</a>
                    <a href="<c:url value='/admin/randomSell'/>" class="btn btn-purple">所有用户随机出售资产</a>
                    <a href="<c:url value='/admin/madeInHaven'/>" class="btn btn-danger">「Made In Haven!」</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
