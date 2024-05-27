<%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/25
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Investment Record List</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&amp;display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
    </style>

    <script>
        $(document).ready(function () {
            $('#searchButton').on('click', function (event) {
                event.preventDefault(); // 阻止表单提交

                var investmentId = $('#investmentId').val().toLowerCase();
                var status = $('#status').val().toLowerCase();
                var userId = $('#userId').val().toLowerCase();
                var operation = $('#operation').val().toLowerCase();

                $('#recordTable tbody tr').filter(function () {
                    var rowText = $(this).text().toLowerCase();
                    var matchesInvestmentId = investmentId === "" || rowText.indexOf(investmentId) > -1;
                    var matchesStatus = status === "" || rowText.indexOf(status) > -1;
                    var matchesUserId = userId === "" || rowText.indexOf(userId) > -1;
                    var matchesOperation = operation === "" || rowText.indexOf(operation) > -1;

                    $(this).toggle(matchesInvestmentId && matchesStatus && matchesUserId && matchesOperation);
                });
            });
        });
    </script>
</head>
<body class="bg-blue-100">
<div class="min-h-screen flex items-center justify-center">
    <div class="bg-white p-20 rounded-lg shadow-xl w-full max-w-2xl mx-auto">
        <h1 class="text-2xl font-semibold text-center text-gray-700 mb-6">投资记录列表</h1>
        <div>
            <!-- 搜索表单 -->
            <form class="mb-6">
                <div class="mb-4">
                    <label for="investmentId" class="block text-gray-700 text-sm font-semibold mb-2">投资ID:</label>
                    <input type="text" id="investmentId"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"/>
                </div>
                <div class="mb-4">
                    <label for="status" class="block text-gray-700 text-sm font-semibold mb-2">状态:</label>
                    <input type="text" id="status" class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"/>
                </div>
                <div class="mb-4">
                    <label for="userId" class="block text-gray-700 text-sm font-semibold mb-2">用户ID:</label>
                    <input type="text" id="userId" class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"/>
                </div>

                <div class="mb-4">
                    <label for="operation" class="block text-gray-700 text-sm font-semibold mb-2">操作:</label>
                    <select id="operation" class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
                        <option value="">全部</option>
                        <option value="买入">买入</option>
                        <option value="卖出">卖出</option>
                    </select>
                </div>
                <button id="searchButton"
                        class="w-full bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">
                    搜索
                </button>
            </form>

        </div>

        <!-- 如果有投资记录信息，则显示表格 -->
        <c:if test="${not empty investmentRecords}">
            <div class="overflow-x-auto">
                <table id="recordTable" class="w-full border-collapse border border-gray-300">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="border border-gray-300 px-4 py-2">投资记录ID</th>
                        <th class="border border-gray-300 px-4 py-2">投资ID</th>
                        <th class="border border-gray-300 px-4 py-2">用户ID</th>
                        <th class="border border-gray-300 px-4 py-2">交易份额</th>
                        <th class="border border-gray-300 px-4 py-2">当前市值</th>
                        <th class="border border-gray-300 px-4 py-2">操作</th>
                        <th class="border border-gray-300 px-4 py-2">状态</th>
                        <th class="border border-gray-300 px-4 py-2">创建时间</th>
                        <th class="border border-gray-300 px-4 py-2">更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="record" items="${investmentRecords}">
                        <tr>
                            <td class="border border-gray-300 px-4 py-2">${record.investmentRecordId}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.investmentId}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.userId}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.amount}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.currentPrize}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.operation}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.status}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.createdAt}</td>
                            <td class="border border-gray-300 px-4 py-2">${record.updatedAt}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <!-- 如果没有投资记录信息，显示提示消息 -->
        <c:if test="${empty investmentRecords}">
            <div class="message text-center text-gray-500 mt-6">没有找到投资记录信息。</div>
        </c:if>
    </div>
</div>
</body>
</html>
