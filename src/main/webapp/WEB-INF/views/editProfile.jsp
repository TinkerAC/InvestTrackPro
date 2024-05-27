<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>编辑用户资料</title>
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
            <button onclick="window.location.href='${pageContext.request.contextPath}/'"
                    class="bg-blue-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
                主页
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/logout'"
                    class="bg-blue-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-4">
                登出
            </button>
        </div>
    </nav>
</header>
<div class="min-h-screen flex items-center justify-center mt-4 mb-4">
    <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-lg">
        <div class="text-center mb-6">
            <h1 class="text-2xl font-semibold text-gray-700">编辑用户资料</h1>
        </div>
        <form action="${pageContext.request.contextPath}/user/edit" method="post" class="space-y-4">
            <div>
                <label for="username" class="block text-gray-700">用户名</label>
                <input type="text" id="username" name="username" value="${sessionScope.user.username}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="email" class="block text-gray-700">邮箱</label>
                <input type="email" id="email" name="email" value="${sessionScope.user.email}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="address" class="block text-gray-700">密码</label>
                <input type="text" id="password" name="password" placeholder="输入您的新密码" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="phone" class="block text-gray-700">电话</label>
                <input type="text" id="phone" name="phone" value="${sessionScope.user.phone}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="address" class="block text-gray-700">地址</label>
                <input type="text" id="address" name="address" value="${sessionScope.user.address}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="firstName" class="block text-gray-700">名</label>
                <input type="text" id="firstName" name="firstName" value="${sessionScope.user.firstName}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>
            <div>
                <label for="lastName" class="block text-gray-700">姓氏</label>
                <input type="text" id="lastName" name="lastName" value="${sessionScope.user.lastName}" class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            </div>

            <div class="text-center">
                <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50">
                    保存
                </button>
                <button type="reset" class="bg-gray-500 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 ml-4">
                    重置
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Tailwind CSS JS -->
<script src="https://cdn.tailwindcss.com"></script>
</body>
<jsp:include page="footer.jsp"/>
</html>
