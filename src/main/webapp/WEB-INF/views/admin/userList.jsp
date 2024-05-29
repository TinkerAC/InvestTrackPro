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


        //控制模态框，编辑用户信息/删除用户
        document.addEventListener('DOMContentLoaded', function () {
            const modal = document.getElementById('myModal');
            const cancelButton = document.getElementById('cancelButton');
            const editForm = document.getElementById('editForm');
            let currentUserId = null;

            document.addEventListener('click', function (event) {
                if (event.target.classList.contains('edit-button')) {
                    // 显示模态框
                    modal.classList.remove('hidden');
                    // 获取当前行的数据
                    const row = event.target.closest('tr');
                    const userId = row.children[0].textContent;
                    const username = row.children[1].textContent;
                    const phone = row.children[3].textContent;

                    // 填充表单数据
                    document.getElementById('username-edit').value = username;
                    document.getElementById('phone-edit').value = phone;

                    currentUserId = userId; // 记录当前用户ID
                }

                if (event.target.classList.contains('delete-button')) {
                    const row = event.target.closest('tr');
                    const userId = row.children[0].textContent;

                    if (confirm("确定删除用户?")) {
                        // 用户点击了“确定”按钮
                        fetch('<%=request.getContextPath()%>/admin/deleteUser', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({ userId: userId }),
                        })
                            .then(response => response.json())
                            .then(data => {
                                console.log('Success:', data);
                                // 如果需要，可以在这里刷新表格数据或更新页面
                                row.remove(); // 成功删除后从表格中移除该行
                            })
                            .catch((error) => {
                                console.error('Error:', error);
                            });
                    } else {
                        // 用户点击了“取消”按钮
                        console.log('用户取消删除操作');
                    }
                }



            });


            cancelButton.addEventListener('click', function () {
                modal.classList.add('hidden');
            });

            editForm.addEventListener('submit', function (event) {
                event.preventDefault();

                // 收集表单数据
                const data = {
                    userId: currentUserId,
                    phone: document.getElementById('phone-edit').value,
                    userName: document.getElementById('username-edit').value,
                    address: document.getElementById('address-edit').value,
                    role: document.getElementById('role-edit').value

                    // 如果需要其他字段可以加上
                };

                // 发送数据到后端
                fetch('<%=request.getContextPath()%>/admin/editUser', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Success:', data);
                        modal.classList.add('hidden');
                        // 如果需要，可以在这里刷新表格数据或更新页面
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
            });
        });






    </script>
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
    <div class="bg-white p-20 rounded-lg shadow-xl w-full max-w-5xl mx-auto overflow-x-auto">
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

        <!-- 收集用户信息的模态框 -->
        <div id="myModal" class="fixed z-10 inset-0 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog"
             aria-modal="true">
            <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
                <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>

                <div class="inline-block align-bottom bg-white rounded-lg px-4 pt-5 pb-4 text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full sm:p-6">
                    <div>
                        <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                            编辑信息
                        </h3>
                        <div class="mt-2">
                            <form id="editForm">
                                <div class="mb-4">
                                    <label for="username-edit"
                                           class="block text-sm font-medium text-gray-700">用户名</label>
                                    <input type="text" name="username" id="username-edit"
                                           class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                                </div>
                                <div class="mb-4">
                                    <label for="phone-edit"
                                           class="block text-sm font-medium text-gray-700">手机号码</label>
                                    <input type="tel" name="phone" id="phone-edit"
                                           class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                                </div>
                                <div class="mb-4">
                                    <label for="address-edit"
                                           class="block text-sm font-medium text-gray-700">地址</label>
                                    <input type="text" name="address" id="address-edit"
                                           class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                                </div>
                                <div class="mb-4">
                                    <label for="role-edit"
                                           class="block text-sm font-medium text-gray-700">用户组</label>
                                    <select name="role" id="role-edit">
                                        <option value="user">用户</option>
                                        <option value="admin">管理员</option>
                                    </select>
                                </div>


                                <div class="flex justify-end">
                                    <button type="button" id="cancelButton"
                                            class="bg-gray-500 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 mr-2">
                                        取消
                                    </button>
                                    <button type="submit"
                                            class="bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">
                                        提交
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- 如果有用户信息，则显示表格 -->
        <c:if test="${not empty users}">
            <div class="overflow-x-auto">
                <table id="userTable" class="w-full border-collapse border border-gray-300">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="border border-gray-300 px-4 py-2">用户ID</th>
                        <th class="border border-gray-300 px-4 py-2">用户名</th>
                        <th class="border border-gray-300 px-4 py-2">邮箱</th>
                        <th class="border border-gray-300 px-4 py-2">电话</th>
                        <th class="border border-gray-300 px-4 py-2">角色</th>
                        <th class="border border-gray-300 px-4 py-2">创建时间</th>
                        <th class="border border-gray-300 px-4 py-2">更新时间</th>
                        <th class="border border-gray-300 px-4 py-2">操作</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td class="border border-gray-300 px-4 py-2">${user.userId}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.username}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.email}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.phone}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.role}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.createdAt}</td>
                            <td class="border border-gray-300 px-4 py-2">${user.updatedAt}</td>
                            <td>
                                <button class="edit-button bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50">
                                    编辑
                                </button>
                                <button class="delete-button bg-red-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50">
                                    删除
                                </button>
                            </td>
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
