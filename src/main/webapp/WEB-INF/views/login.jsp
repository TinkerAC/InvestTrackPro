<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/18
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvestTrack Pro - 登录页</title>
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
<body class="bg-blue-100 flex items-center justify-center min-h-screen">
<div class="bg-white shadow-xl rounded-lg overflow-hidden flex flex-col max-w-md w-full">

    <div class="w-full p-6">
        <h1 class="text-2xl font-semibold text-gray-800 text-center mb-6">登录页</h1>
        <%
            String e = (String) request.getAttribute("error");
            if (e != null && !e.isEmpty()) {
        %>
        <div class="bg-red-100 text-red-700 p-3 rounded mb-4 text-center">
            <%= e %>
        </div>
        <%
            }
        %>
        <form action="login" method="post" class="space-y-4">
            <div>
                <label for="email" class="block text-gray-700 text-sm font-semibold mb-2">用户名:</label>
                <input type="text" id="email" name="email" required class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
            </div>
            <div>
                <label for="password" class="block text-gray-700 text-sm font-semibold mb-2">密码:</label>
                <input type="password" id="password" name="password" required class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
            </div>
            <div class="flex items-center justify-between">
                <div>
                    <input type="checkbox" id="remember" name="remember" class="mr-2 leading-tight">
                    <label for="remember" class="text-sm text-gray-600">记住我</label>
                </div>
                <div>
                    <a href="#" class="text-sm text-indigo-600 hover:underline">忘记密码？</a>
                </div>
            </div>
            <div>
                <input type="submit" value="登录" class="w-full bg-indigo-600 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50 cursor-pointer">
            </div>
        </form>
        <div class="mt-6 text-center">
            <h1 class="text-sm text-gray-600">还没有账号？<a href="register" class="text-indigo-600 hover:underline">注册</a></h1>
        </div>
        <div class="mt-6 text-center">
            <p class="text-gray-500 text-sm">或通过社交账号登录</p>
            <div class="flex justify-center space-x-4 mt-4">
                <a href="#" class="text-gray-600 hover:text-gray-800">
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2.04c-5.52 0-10 4.48-10 10 0 4.99 3.66 9.12 8.44 9.88v-6.98h-2.54v-2.9h2.54v-2.23c0-2.5 1.52-3.88 3.73-3.88 1.06 0 1.97.08 2.23.11v2.58h-1.53c-1.2 0-1.43.57-1.43 1.4v1.84h2.85l-.37 2.9h-2.48v6.98c4.78-.76 8.44-4.89 8.44-9.88 0-5.52-4.48-10-10-10z"/></svg>
                </a>
                <a href="#" class="text-gray-600 hover:text-gray-800">
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24"><path d="M24 4.56c-.89.39-1.85.65-2.86.77 1.03-.61 1.82-1.58 2.19-2.73-.97.57-2.04.98-3.18 1.2-.92-.98-2.24-1.6-3.7-1.6-2.8 0-5.07 2.27-5.07 5.07 0 .4.05.8.13 1.18-4.22-.21-7.96-2.23-10.47-5.29-.44.75-.7 1.6-.7 2.52 0 1.74.89 3.28 2.22 4.19-.82-.03-1.58-.25-2.25-.62v.06c0 2.44 1.74 4.47 4.04 4.93-.42.11-.87.17-1.33.17-.32 0-.63-.03-.94-.09.63 1.96 2.45 3.39 4.61 3.43-1.68 1.32-3.8 2.1-6.1 2.1-.39 0-.77-.02-1.15-.07 2.18 1.39 4.77 2.2 7.55 2.2 9.05 0 14-7.5 14-14v-.64c.95-.68 1.78-1.53 2.44-2.5z"/></svg>
                </a>
                <a href="#" class="text-gray-600 hover:text-gray-800">
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24"><path d="M23.954 4.569c-.885.385-1.83.644-2.825.759 1.014-.608 1.793-1.574 2.163-2.723-.951.566-2.005.978-3.127 1.2-.896-.956-2.173-1.555-3.591-1.555-2.719 0-4.92 2.201-4.92 4.917 0 .39.042.765.126 1.124-4.088-.205-7.719-2.164-10.141-5.144-.423.729-.666 1.577-.666 2.476 0 1.708.869 3.213 2.188 4.096-.807-.026-1.566-.248-2.228-.617v.061c0 2.385 1.698 4.374 3.95 4.828-.413.112-.849.171-1.296.171-.316 0-.623-.03-.924-.086.631 1.956 2.445 3.379 4.6 3.418-1.684 1.318-3.809 2.103-6.102 2.103-.396 0-.788-.023-1.177-.068 2.179 1.396 4.768 2.211 7.557 2.211 9.056 0 14.002-7.496 14.002-13.986 0-.21-.004-.423-.014-.635.962-.695 1.797-1.562 2.457-2.549z"/></svg>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
