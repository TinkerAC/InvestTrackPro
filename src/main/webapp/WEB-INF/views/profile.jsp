<%@ page import="com.zufe.cpy.investtrackpro.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人资料</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body class="bg-blue-100">
<div class="min-h-screen flex items-center justify-center">
    <div class="bg-white p-10 rounded-lg shadow-xl w-full max-w-md">
        <div class="text-center mb-6">
            <h1 class="text-2xl font-semibold text-gray-700">个人资料</h1>
        </div>
        <div class="text-center">
            <%
                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {
            %>
            <h2 class="text-xl font-medium text-gray-700">您好，<%= user.getUsername() %>
            </h2>
            <p class="text-gray-600">您的邮箱是：<%= user.getEmail() %>
            </p>
            <%
            } else {
            %>
            <h2 class="text-xl font-medium text-red-600">您尚未登录</h2>
            <%
                }
            %>
        </div>
        <div class="mt-6 text-center">

            <button onclick="window.location.href='${pageContext.request.contextPath}/user/logout'"
                    class="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">
                注销
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/investment'"
                    class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 ml-4">
                投资管理
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/user/edit'"
                    class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 ml-4">
                编辑资料
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/asset/view'"
                    class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 ml-4">
                我的资产
            </button>

        </div>
    </div>
</div>

<!-- Tailwind CSS JS -->
<script src="https://cdn.tailwindcss.com"></script>
</body>
<jsp:include page="footer.jsp"/>
</html>

