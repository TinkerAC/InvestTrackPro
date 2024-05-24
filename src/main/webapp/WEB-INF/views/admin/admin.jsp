<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员页面</title>
</head>

<body>
<h1>管理员页面</h1>
<p>欢迎您，${user.username}！</p>
<p>您的邮箱是：${user.email}</p>
<p>您的身份是：${user.role}</p>

<button onclick="location.href=<%=request.getContextPath()+"/admin/logout"%>">退出登录</button>
<button onclick="location.href=<%=request.getContextPath()+"/admin/user"%>">查看用户列表</button>
<button onclick="location.href=<%=request.getContextPath()+"/admin/investment"%>">查看投资列表</button>
<button onclick="location.href=<%=request.getContextPath()+"/admin/investmentRecord"%>">查看交易记录</button>
<button onclick="location.href=<%=request.getContextPath()+"/admin/madeInHaven"%>">「Made In Haven!」</button>
<button onclick="location.href=<%=request.getContextPath()+"/admin/resetSystem"%>">重置系统</button>


</body>
</html>
