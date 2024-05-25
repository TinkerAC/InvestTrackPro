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

<form action="<%=request.getContextPath()%>/admin/logout" method="POST" style="display: inline;">
    <button type="submit">退出登录</button>
</form>
<form action="<%=request.getContextPath()%>/admin/user" method="POST" style="display: inline;">
    <button type="submit">查看用户列表</button>
</form>
<form action="<%=request.getContextPath()%>/admin/investment" method="POST" style="display: inline;">
    <button type="submit">查看投资列表</button>
</form>
<form action="<%=request.getContextPath()%>/admin/investmentRecord" method="POST" style="display: inline;">
    <button type="submit">查看交易记录</button>
</form>
<form action="<%=request.getContextPath()%>/admin/madeInHaven" method="POST" style="display: inline;">
    <button type="submit">「Made In Haven!」</button>
</form>
<form action="<%=request.getContextPath()%>/admin/resetSystem" method="POST" style="display: inline;">
    <button type="submit">重置系统</button>
</form>


</body>
</html>
