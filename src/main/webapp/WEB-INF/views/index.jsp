<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap JS和Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        /* 自定义轮播图样式 */
        .carousel-item img {
            height: 420px;
            object-fit: cover;
        }

        /* 响应式布局 */
        @media (max-width: 768px) {
            .carousel-item img {
                height: 300px;
            }
        }
    </style>
</head>
<body class="bg-blue-100">
<!-- 顶端导航栏 -->
<nav class="bg-white shadow-md">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <div class="text-xl font-bold text-gray-800">InvestTrackPro</div>
        <div>
            <c:if test="${empty sessionScope.user}">
                <button class="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50"
                        onclick="location.href='user/register'">注册
                </button>
                <button class="bg-gray-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 ml-2"
                        onclick="location.href='user/login'">登录
                </button>
            </c:if>
            <c:if test="${not empty sessionScope.user}">

                <button class="bg-blue-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-2"
                        onclick="location.href='user/logout'">注销
                </button>
                <button class="bg-blue-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 ml-2"
                        onclick="location.href='user/profile'">个人资料
                </button>
            </c:if>
        </div>
    </div>
</nav>

<!-- 轮播图 -->
<div id="myCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>

    <!-- The slideshow -->
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="../images/image4.jpeg" class="d-block w-100" alt="Image 1">
        </div>
        <div class="carousel-item">
            <img src="../images/image5.jpeg" class="d-block w-100" alt="Image 2">
        </div>
        <div class="carousel-item">
            <img src="../images/image6.jpg" class="d-block w-100" alt="Image 3">
        </div>
    </div>

    <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<!-- 主体内容 -->
<div class="container mx-auto px-6 py-16">
    <div class="text-center">
        <h1 class="text-4xl font-semibold text-gray-800">欢迎来到 InvestTrackPro</h1>
        <p class="text-gray-600 mt-4">
            管理和跟踪您的投资，从未如此简单。通过我们的平台，您可以方便地查看投资项目，管理个人资产，并获得及时的市场信息。</p>
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
