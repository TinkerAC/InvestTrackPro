<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>InvestTrackPro</title>
    <!-- 样式和脚本 -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            margin-top: 50px;
            flex: 1; /* 让 .container 占据剩余空间 */
        }

        .btn-custom {
            margin-right: 10px;
        }

        .header {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header text-center">
        <h1>澳门新葡京酒店</h1>
        <br/>
        <h2>点击下面的按钮操作!</h2>
    </div>
    <div class="text-center">
        <button class="btn btn-primary btn-custom" onclick="location.href='user/register'">注册</button>
        <button class="btn btn-secondary btn-custom" onclick="location.href='user/login'">登录</button>
        <button class="btn btn-success btn-custom" onclick="location.href='investment'">查看投资项目</button>
        <button class="btn btn-warning btn-custom" onclick="location.href='user/login_dev'">调试专用,一键登录</button>
        <button class="btn btn-danger btn-custom" onclick="location.href='asset/view'">我的资产</button>
        <button class="btn btn-info btn-custom" onclick="location.href='user/logout'">注销</button>
    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
