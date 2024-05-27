<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<table class="w-full border-collapse border border-gray-300">
    <thead>
    <tr class="bg-gray-100">
        <th class="border border-gray-300 px-4 py-2">投资ID</th>
        <th class="border border-gray-300 px-4 py-2">投资名称</th>
        <th class="border border-gray-300 px-4 py-2">初始市值</th>
        <th class="border border-gray-300 px-4 py-2">当前市值</th>
        <th class="border border-gray-300 px-4 py-2">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="investment" items="${investments}">
        <tr>
            <td class="border border-gray-300 px-4 py-2">${investment.investmentId}</td>
            <td class="border border-gray-300 px-4 py-2">${investment.name}</td>
            <td class="border border-gray-300 px-4 py-2">${investment.initialValue}</td>
            <td class="border border-gray-300 px-4 py-2">${investment.currentValue}</td>
            <td class="border border-gray-300 px-4 py-2"><a href='investment/details?id=${investment.investmentId}' class="text-indigo-600 hover:underline">查看详情</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
