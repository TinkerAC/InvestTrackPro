<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户管理系统</title>
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
        // 前端搜索功能
        $(document).ready(function () {

            $('#searchButton').on('click', function (event) {
                event.preventDefault(); // 阻止表单提交

                var username = $('#username').val().toLowerCase();
                var email = $('#newEmail').val().toLowerCase();
                var phone = $('#newPhone').val().toLowerCase();
                var role = $('#newRole').val().toLowerCase();

                $('#userTable tbody tr').filter(function () {
                    var rowText = $(this).text().toLowerCase();
                    var matchesUsername = username === "" || rowText.indexOf(username) > -1;
                    var matchesEmail = email === "" || rowText.indexOf(email) > -1;
                    var matchesPhone = phone === "" || rowText.indexOf(phone) > -1;
                    var matchesRole = role === "" || rowText.indexOf(role) > -1;

                    $(this).toggle(matchesUsername && matchesEmail && matchesPhone && matchesRole);
                });
            });
        });


        // 使用AJAX提交表单
        $('form').on('submit', function (event) {
            event.preventDefault(); // 阻止默认表单提交

            var formData = $(this).serialize(); // 序列化表单数据

            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: formData,
                success: function (response) {
                    // 在这里处理成功响应
                    alert('用户已成功添加');
                    // 例如，您可以更新表格或显示成功消息
                },
                error: function (xhr, status, error) {
                    // 在这里处理错误响应
                    alert('添加用户时出错: ' + error);
                }
            });
        });

    </script>
</head>
<body class="bg-blue-100">
<div class="min-h-screen flex items-center justify-center">
    <div class="bg-white p-20 rounded-lg shadow-xl w-full max-w-2xl mx-auto">
        <h1 class="text-2xl font-semibold text-center text-gray-700 mb-6">用户列表</h1>
        <div>
            <!-- 搜索和新增用户的表单 -->
            <form action="<%=request.getContextPath()%>/user/register_ajax" method="POST" class="mb-6">
                <div class="mb-4">
                    <label for="username" class="block text-gray-700 text-sm font-semibold mb-2">用户名:</label>
                    <input type="text" name="username" id="username"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"/>
                </div>
                <div class="mb-4">
                    <label for="newPassword" class="block text-gray-700 text-sm font-semibold mb-2">密码:</label>
                    <input type="password" name="newPassword" id="newPassword"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm" required/>
                </div>
                <div class="mb-4">
                    <label for="newEmail" class="block text-gray-700 text-sm font-semibold mb-2">邮箱:</label>
                    <input type="email" name="newEmail" id="newEmail"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm" required/>
                </div>
                <div class="mb-4">
                    <label for="newPhone" class="block text-gray-700 text-sm font-semibold mb-2">电话:</label>
                    <input type="text" name="newPhone" id="newPhone"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm" required/>
                </div>
                <div class="mb-4">
                    <label for="newRole" class="block text-gray-700 text-sm font-semibold mb-2">角色:</label>
                    <select name="newRole" id="newRole"
                            class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
                        <option value="">请选择</option>
                        <option value="user">用户</option>
                        <option value="admin">管理员</option>
                    </select>
                </div>

                <div class="flex justify-between">
                    <button id="searchButton"
                            class="w-full bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50 mr-2">
                        搜索
                    </button>

                </div>
            </form>
        </div>

        <!-- 如果有用户信息，则显示表格 -->
        <c:if test="${not empty users}">
            <div class="overflow-x-auto">
                <table id="userTable" class="w-full border-collapse border border-gray-300">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="border border-gray-300 px-4 py-2">用户ID</th>
                        <th class="border border-gray-300 px-4 py-2">用户名</th>
                        <th class="border border-gray-300 px-4 py-2">密码</th>
                        <th class="border border-gray-300 px-4 py-2">邮箱</th>
                        <th class="border border-gray-300 px-4 py-2">电话</th>
                        <th class="border border-gray-300 px-4 py-2">角色</th>
                        <th class="border border-gray-300 px-4 py-2">创建时间</th>
                        <th class="border border-gray-300 px-4 py-2">更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td class="border border-gray-300 px-4 py-2">${user.userId}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.username}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.password}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.email}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.phone}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.role}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.createdAt}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.updatedAt}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <!-- 如果没有用户信息，显示提示消息 -->
        <c:if test="${empty users}">
            <div class="message text-center text-gray-500 mt-6">没有找到用户信息。</div>
        </c:if>
    </div>
</div>
</body>
</html>
