<%@ page import="com.zufe.cpy.investtrackpro.model.Investment" %>
<%@ page import="javax.swing.*" %>
<%@ page import="com.zufe.cpy.investtrackpro.model.User" %><%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/19
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>详情页</title>
</head>
<body>
<h1>投资详情</h1>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user != null) {
        out.println("<h1>欢迎+" + user.getUsername() + "+</h1>");

        Investment investment = (Investment) request.getAttribute("investment");
        if (investment != null) {
            out.println("<h2>投资名称：" + investment.getName() + "</h2>");
            out.println("<h2>初始市值：" + investment.getInitialValue() + "</h2>");
            out.println("<h2>当前市值：" + investment.getCurrentValue() + "</h2>");

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }
    } else {
        out.println("<h2>您尚未登录</h2>");
        out.println("<a href='user/login'>去登录</a>");
    }


%>

</body>
</html>
