<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员页面</title>
    <!-- Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center mb-8">管理员页面</h1>
    <div class="bg-white p-6 rounded-lg shadow-lg">
        <c:if test="${not empty requestScope.message}">
            <p class="text-red-500 text-lg mb-4">${requestScope.message}</p>
        </c:if>
        <p class="text-xl mb-4">欢迎您，<span class="font-semibold">${sessionScope.user.username}</span>！</p>
        <p class="text-lg mb-4">您的邮箱是：<span class="text-blue-600">${sessionScope.user.email}</span></p>
        <p class="text-lg mb-8">您的身份是：<span class="text-green-600">${sessionScope.user.role}</span></p>

        <div class="flex flex-wrap justify-center space-x-4">
            <form action="<%=request.getContextPath()%>/admin/logout" method="POST">
                <button type="submit" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
                    退出登录
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/user" method="POST">
                <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    查看用户列表
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/investment" method="get">
                <button type="submit" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                    查看投资列表
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/investmentRecord" method="POST">
                <button type="submit" class="bg-yellow-500 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded">
                    查看交易记录
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/madeInHaven" method="POST">
                <button type="submit" class="bg-purple-500 hover:bg-purple-700 text-white font-bold py-2 px-4 rounded">
                    「Made In Haven!」
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/asset/view" method="POST">
                <button type="submit" class="bg-purple-500 hover:bg-purple-700 text-white font-bold py-2 px-4 rounded">
                    查看资产
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/resetSystem" method="POST">
                <button type="submit" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded">
                    重置系统
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/randomUser" method="POST">
                <button type="submit" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded">
                    新增5位随机用户
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/randomBuy" method="POST">
                <button type="submit" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded">
                    所有用户随机购买投资
                </button>
            </form>
            <form action="<%=request.getContextPath()%>/admin/randomSell" method="POST">
                <button type="submit" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded">
                    所有用户随机出售资产
                </button>
            </form>

        </div>
    </div>
</div>
</body>
</html>
