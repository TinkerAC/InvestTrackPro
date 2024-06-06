<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvestTrack Pro - 登录页</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"/>
    <style>
        body {
            background-image: url("<c:url value='/images/login-bg.jpg'/>");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Check for cookies and populate the form
            const email = getCookie("email");
            const password = getCookie("password");
            if (email && password) {
                document.getElementById("email").value = email;
                document.getElementById("password").value = password;
                document.getElementById("remember").checked = true;
            }

            // Form submission event listener
            document.getElementById("loginForm").addEventListener("submit", function(event) {
                const rememberMe = document.getElementById("remember").checked;
                const email = document.getElementById("email").value;
                const password = document.getElementById("password").value;

                if (rememberMe) {
                    setCookie("email", email, 7); // Save email and password for 7 days
                    setCookie("password", password, 7);
                } else {
                    deleteCookie("email");
                    deleteCookie("password");
                }
            });
        });

        function setCookie(name, value, days) {
            const expires = new Date(Date.now() + days * 864e5).toUTCString();
            document.cookie = name + "=" + encodeURIComponent(value) + "; expires=" + expires + "; path=/";
        }

        function getCookie(name) {
            return document.cookie.split("; ").reduce((r, v) => {
                const parts = v.split("=");
                return parts[0] === name ? decodeURIComponent(parts[1]) : r;
            }, "");
        }

        function deleteCookie(name) {
            setCookie(name, "", -1);
        }
    </script>
</head>
<body class="bg-blue-100 flex items-center justify-center min-h-screen">
<div class="bg-white shadow-xl rounded-lg overflow-hidden flex flex-col max-w-md w-full">
    <div class="w-full p-6">
        <h1 class="text-2xl font-semibold text-gray-800 text-center mb-6">登录页</h1>
        <c:if test="${not empty error}">
            <div class="bg-red-100 text-red-700 p-3 rounded mb-4 text-center">
                    ${error}
            </div>
        </c:if>
        <form id="loginForm" action="login" method="post" class="space-y-4">
            <div>
                <label for="email" class="block text-gray-700 text-sm font-semibold mb-2">邮箱:</label>
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
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2.04c-5.52 0-10 4.48-10 10 0 4.99 3.66 9.12 8.44 9.88v-6.98h-2.54v-2.9h2.54v-2.23c0-2.5 1.52-3.88 3.73-3.88 1.06 0 1.97.08 2.23.11v2.58h-1.53c-1.2 0-1.43.57-1.43 1.4v1.84h2.85l-.37 2.9h-2.48v6.98c4.78-.76 8.44-4.89 8.44-9.88 0-5.52-4.48-10-10-10z"></path></svg>
                </a>
                <a href="#" class="text-gray-600 hover:text-gray-800">
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24"><path d="M24 4.56c-.89.39-1.85.65-2.86.77 1.03-.61 1.82-1.58 2.19-2.73-.97.57-2.04.98-3.18 1.2-.92-.98-2.24-1.6-3.7-1.6-2.8 0-5.07 2.27-5.07 5.07 0 .4.05.8.13 1.18-4.22-.21-7.96-2.23-10.47-5.29-.44.75-.7 1.6-.7 2.52 0 1.74.89 3.28 2.22 4.19-.82-.03-1.58-.25-2.25-.62v.06c0 2.44 1.74 4.47 4.04 4.93-.42.11-.87.17-1.33.17-.32 0-.63-.03-.94-.09.63 1.96 2.45 3.39 4.61 3.43-1.68 1.32-3.8 2.1-6.1 2.1-.39 0-.77-.02-1.15-.07 2.18 1.39 4.77 2.2 7.55 2.2 9.05 0 14-7.5 14-14v-.64c.95-.68 1.78-1.53 2.44-2.5z"></path></svg>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
