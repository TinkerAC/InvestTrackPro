<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvestTrackPro - 首页</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- 添加Font Awesome图标库 -->
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
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
<!-- 顶端导航栏 -->
<nav class="bg-white shadow-md">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <div class="text-xl font-bold text-gray-800">InvestTrackPro</div>
        <div>
            <button class="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50" onclick="location.href='user/register'">注册</button>
            <button class="bg-gray-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 ml-2" onclick="location.href='user/login'">登录</button>
            <button class="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 ml-2" onclick="location.href='investment'">查看投资项目</button>
            <button class="bg-yellow-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-yellow-700 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-opacity-50 ml-2" onclick="location.href='user/login_dev'">调试专用,一键登录</button>
            <button class="bg-red-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50 ml-2" onclick="location.href='asset/view'">我的资产</button>
            <button class="bg-blue-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-2" onclick="location.href='user/logout'">注销</button>
        </div>
    </div>
</nav>

<!-- 主体内容 -->
<div class="container mx-auto px-6 py-16">
    <div class="text-center">
        <h1 class="text-4xl font-semibold text-gray-800">欢迎来到 InvestTrackPro</h1>
        <p class="text-gray-600 mt-4">管理和跟踪您的投资，从未如此简单。通过我们的平台，您可以方便地查看投资项目，管理个人资产，并获得及时的市场信息。</p>
    </div>
    <div class="mt-10 text-center">
        <h2 class="text-2xl font-semibold text-gray-800">我们的特色功能</h2>
        <p class="text-gray-600 mt-4">探索我们为您提供的一系列功能，帮助您轻松管理投资。</p>
        <div class="mt-8 flex flex-wrap justify-center">
            <div class="bg-white p-6 rounded-lg shadow-md w-full md:w-1/3 m-4 transform hover:scale-105 transition-transform duration-300">
                <div class="text-center">
                    <div class="bg-indigo-500 text-white w-16 h-16 flex items-center justify-center rounded-full mx-auto">
                        <i class="fas fa-chart-line text-3xl"></i>
                    </div>
                    <h3 class="text-xl font-medium text-gray-800 mt-4">实时投资追踪</h3>
                    <p class="text-gray-600 mt-2">随时随地跟踪您的投资表现，获取最新市场动态。</p>
                </div>
            </div>
            <div class="bg-white p-6 rounded-lg shadow-md w-full md:w-1/3 m-4 transform hover:scale-105 transition-transform duration-300">
                <div class="text-center">
                    <div class="bg-green-500 text-white w-16 h-16 flex items-center justify-center rounded-full mx-auto">
                        <i class="fas fa-wallet text-3xl"></i>
                    </div>
                    <h3 class="text-xl font-medium text-gray-800 mt-4">资产管理</h3>
                    <p class="text-gray-600 mt-2">全面管理您的个人资产，轻松查看资产组合。</p>
                </div>
            </div>
            <div class="bg-white p-6 rounded-lg shadow-md w-full md:w-1/3 m-4 transform hover:scale-105 transition-transform duration-300">
                <div class="text-center">
                    <div class="bg-yellow-500 text-white w-16 h-16 flex items-center justify-center rounded-full mx-auto">
                        <i class="fas fa-lightbulb text-3xl"></i>
                    </div>
                    <h3 class="text-xl font-medium text-gray-800 mt-4">专业建议</h3>
                    <p class="text-gray-600 mt-2">获取专业的投资建议和市场分析，帮助您做出明智决策。</p>
                </div>
            </div>
        </div>
    </div>
</div>





</body>
<jsp:include page="footer.jsp"/>
</html>
