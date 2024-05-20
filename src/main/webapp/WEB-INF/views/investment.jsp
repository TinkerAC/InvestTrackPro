<%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/19
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.zufe.cpy.investtrackpro.model.User" %>
<%@ page import="com.zufe.cpy.investtrackpro.model.Investment" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>投资管理系统</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <-- 使用 jQuery 实现 AJAX 搜索 -->
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
<h1>投资列表</h1>


<c:choose>
    <c:when test="${not empty user}">
        <h1>欢迎, ${user.username}!</h1>
        //一个搜索选项表单
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

        //如果有投资信息，则显示表格
        <c:if test="${not empty investments}">

            <table border='1'>
                <tr>
                    <th>投资ID</th>
                    <th>投资名称</th>
                    <th>初始市值</th>
                    <th>当前市值</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="investment" items="${investments}">
                    <tr>
                        <td>${investment.investmentId}</td>
                        <td>${investment.name}</td>
                        <td>${investment.initialValue}</td>
                        <td>${investment.currentValue}</td>
                        <td><a href='investment/details?id=${investment.investmentId}'>查看详情</a></td>
                    </tr>
                </c:forEach>
            </table>

        </c:if>
    </c:when>
    <c:otherwise>
        <h2>您尚未登录</h2>
        <a href='user/login'>去登录</a>
    </c:otherwise>
</c:choose>


</body>
</html>
