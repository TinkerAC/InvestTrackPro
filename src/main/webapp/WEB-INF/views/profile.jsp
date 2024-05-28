<%@ page import="java.util.Date" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page import="com.zufe.cpy.investtrackpro.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人资料</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .bg-custom {
            background-image: url("../images/background.webp");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
    </style>
</head>
<body class="bg-custom">
<header class="bg-white shadow p-6 mb-4">
    <nav class="container mx-auto flex justify-between items-center">
        <h1 class="text-2xl font-semibold text-gray-700">InvestTrackPro</h1>
        <div>
            <button onclick="window.location.href='${pageContext.request.contextPath}/user/profile'"
                    class="bg-blue-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
                用户主页
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/user/logout'"
                    class="bg-blue-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-4">
                注销
            </button>
        </div>
    </nav>
</header>
<div class="min-h-screen flex items-center justify-center mt-2 mb-2">
    <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-lg mt-2 mb-2">
        <div class="text-center mb-6">
            <h1 class="text-2xl font-semibold text-gray-700">个人资料</h1>
        </div>
        <div class="text-center">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <h2 class="text-xl font-medium text-gray-700">您好，${sessionScope.user.username}</h2>
                    <p class="text-gray-600">您的ID是：${sessionScope.user.userId}</p>
                    <p class="text-gray-600">您用户组是：${sessionScope.user.role}</p>
                    <p class="text-gray-600">您的邮箱是：${sessionScope.user.email}</p>
                    <%
                        Date currentTime = new Date();
                        User user = (User) session.getAttribute("user");
                        Date registerTime = (Date) user.getCreatedAt();
                        long diffInMillis = currentTime.getTime() - registerTime.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
                        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                    %>
                    <p class="text-gray-600">
                        注册时长: <%= diffInDays %> 天 <%= diffInHours %> 小时 <%= diffInMinutes %> 分钟
                    </p>


                </c:when>
                <c:otherwise>
                    <h2 class="text-xl font-medium text-red-600">您尚未登录</h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="mt-4 text-center space-y-4">
            <c:if test="${not empty sessionScope.user}">
                <button onclick="window.location.href='${pageContext.request.contextPath}/user/delete'"
                        class="bg-red-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50">
                    删除账户
                </button>
                <button onclick="window.location.href='${pageContext.request.contextPath}/investment'"
                        class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 ml-4">
                    查看投资
                </button>
                <button onclick="window.location.href='${pageContext.request.contextPath}/user/edit'"
                        class="bg-yellow-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-opacity-50 ml-4">
                    编辑资料
                </button>
                <button onclick="window.location.href='${pageContext.request.contextPath}/asset/view'"
                        class="bg-blue-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-4">
                    我的资产
                </button>
            </c:if>
        </div>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
