<%@ page import="com.zufe.cpy.investtrackpro.model.User" %>
<%@ page import="com.zufe.cpy.investtrackpro.model.Investment" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: 20586
  Date: 2024/5/19
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>列表</title>
</head>
<body>
<h1>投资列表</h1>
<%
    User user = (User) request.getSession().getAttribute("user");

    if (user != null) {
        out.println("<h1>欢迎+" + user.getUsername() + "+</h1>");

        out.println("<a href='investment'>投资</a>");
        List<Investment> investments = (List<Investment>) request.getAttribute("investments");

        if (investments != null) {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>投资ID</th>");
            out.println("<th>投资名称</th>");
            out.println("<th>初始市值</th>");
            out.println("<th>当前市值</th>");
            out.println("<th>操作</th>");
            out.println("</tr>");
            for (Investment investment : investments) {
                out.println("<tr>");
                out.println("<td>" + investment.getInvestmentId() + "</td>");
                out.println("<td>" + investment.getName() + "</td>");
                out.println("<td>" + investment.getInitialValue() + "</td>");
                out.println("<td>" + investment.getCurrentValue() + "</td>");
                out.println("<td><a href='investment/details?id=" + investment.getInvestmentId() + "'>查看详情</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

    } else {
        out.println("<h2>您尚未登录</h2>");
        out.println("<a href='user/login'>去登录</a>");
    }


%>

</body>
</html>
