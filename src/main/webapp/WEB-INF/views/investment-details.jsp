<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>详情页</title>
</head>
<body>
<h1>投资详情</h1>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <h1>欢迎 <c:out value="${sessionScope.user.username}"/></h1>

        <c:choose>
            <c:when test="${not empty requestScope.investment}">
                <h2>投资名称：<c:out value="${requestScope.investment.name}"/></h2>
                <h2>初始市值：<c:out value="${requestScope.investment.initialValue}"/></h2>
                <h2>当前市值：<c:out value="${requestScope.investment.currentValue}"/></h2>
                <bb>操作</bb>
                <a href="${pageContext.request.contextPath}/asset/buy?investmentId=${requestScope.investment.investmentId}">买入</a>
            </c:when>
            <c:otherwise>
                <c:redirect url="${pageContext.request.contextPath}/4"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <h2>您尚未登录</h2>
        <a href="${pageContext.request.contextPath}/user/login">去登录</a>
    </c:otherwise>
</c:choose>

</body>
</html>
<jsp:include page="footer.jsp"/>