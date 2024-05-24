<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>投资管理系统</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            color: #333;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            margin: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 800px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        label {
            margin-bottom: 5px;
            color: #666;
        }

        select, button {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        button {
            background-color: #28a745;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #218838;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f8f8f8;
        }

        a {
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .message {
            text-align: center;
            color: #333;
        }
    </style>

    <!-- 使用 jQuery 实现 AJAX 搜索 -->
    <script>
        $(document).ready(function () {
            // 当搜索表单提交时
            $('form').on('submit', function (event) {
                event.preventDefault(); // 阻止表单的常规提交

                // 获取表单数据
                var formData = $(this).serialize(); // 将表单数据序列化

                // 执行 AJAX 请求
                $.ajax({
                    url: 'investment/search',
                    type: 'GET',
                    cache: false,  // 禁用 AJAX 请求的缓存
                    data: formData,
                    success: function (response) {
                        $('table tbody').html(response);
                    },
                    error: function () {
                        alert('搜索失败，请重试。');
                    }
                });
            });
        });
    </script>

</head>
<body>
<div class="container">
    <h1>投资列表</h1>
    <c:choose>
        <c:when test="${not empty requestScope.user}">
            <div class="message">欢迎，${requestScope.user.username}。</div>
        </c:when>
        <c:otherwise>
            <div class="message">您尚未登录!</div>
        </c:otherwise>
    </c:choose>

    <!-- 一个搜索选项表单 -->
    <form action="investment/search" method="GET">
        <label for="category">类型:</label>
        <select name="category" id="category">
            <option value="">不限</option>
            <option value="股票">股票</option>
            <option value="债券">债券</option>
            <option value="基金">基金</option>
            <option value="房地产">房地产</option>
            <option value="商品">大宗商品</option>
        </select>

        <label for="riskLevel">风险等级:</label>
        <select name="riskLevel" id="riskLevel">
            <option value="">不限</option>
            <option value="1">低风险</option>
            <option value="2">中低风险</option>
            <option value="3">中风险</option>
            <option value="4">中高风险</option>
            <option value="5">高风险</option>
        </select>

        <button type="submit">搜索</button>
    </form>
    <!-- 一个新增投资按钮 -->
    <button onclick="location.href='<%=request.getContextPath()+"/investment/add"%>'">新增投资</button>

    <!-- 如果有投资信息，则显示表格 -->
    <c:if test="${not empty investments}">
        <table>
            <thead>
            <tr>
                <th>投资ID</th>
                <th>投资名称</th>
                <th>初始市值</th>
                <th>当前市值</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="investment" items="${investments}">
                <tr>
                    <td>${investment.investmentId}</td>
                    <td>${investment.name}</td>
                    <td>${investment.initialValue}</td>
                    <td>${investment.currentValue}</td>
                    <td>
                        <a href='investment/details?id=${investment.investmentId}'>查看详情</a>
                        <a>href='investment/delete?id=${investment.investmentId}'>删除</a>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- 如果没有投资信息，显示提示消息 -->
    <c:if test="${empty investments}">
        <div class="message">没有找到投资信息。</div>
    </c:if>
</div>
</body>
</html>
