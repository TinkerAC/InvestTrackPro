<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的资产</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="bg-gray-50 text-gray-800 p-6">
<header class="mb-8">
    <nav class="bg-white shadow p-4 rounded-lg flex justify-between items-center">
        <h1 class="text-2xl font-bold text-gray-700">资产管理</h1>
        <div>
            <!-- Personal Home Page Link -->
            <a href="<%=request.getContextPath()%>/user/profile"
               class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
                个人主页
            </a>

            <!-- Logout Link -->
            <a href="<%=request.getContextPath()%>/user/logout"
               class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 ml-4">
                登出
            </a>

        </div>
    </nav>
</header>

<main>
    <h1 class="text-3xl font-bold text-center text-gray-700 mb-8">我的资产</h1>
    <div class="overflow-x-auto">
        <table class="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
            <thead>
            <tr>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">资产id</th>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">投资项目id</th>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">投资项目名</th>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">投资项目分类</th>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">持有数量</th>
                <th class="py-4 px-6 bg-gray-200 text-left text-gray-600 font-medium">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${assets}" var="asset">
                <tr class="hover:bg-gray-100">
                    <td class="py-4 px-6 border-b border-gray-200">${asset.assetId}</td>
                    <td class="py-4 px-6 border-b border-gray-200">${asset.investmentId}</td>
                    <td class="py-4 px-6 border-b border-gray-200">${requestScope.investmentMap.get(asset.investmentId).name}</td>
                    <td class="py-4 px-6 border-b border-gray-200">${requestScope.investmentMap.get(asset.investmentId).category}</td>
                    <td class="py-4 px-6 border-b border-gray-200">${asset.amount}</td>
                    <td class="py-4 px-6 border-b border-gray-200">
                        <button class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600"
                                onclick="window.location.href='${pageContext.request.contextPath}/investment/details?id=${asset.investmentId}'">
                            查看详情
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="mt-6 text-center">
        <button onclick="window.location.href='report'" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
            查看报告
        </button>
    </div>
</main>

<footer class="mt-8">
    <div class="text-center text-gray-600">
        &copy; 2024 InvestTrackPro. 保留所有权利.
    </div>
</footer>
</body>
</html>
