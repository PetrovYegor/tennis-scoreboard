<%--
  Created by IntelliJ IDEA.
  User: Yegor
  Date: 03.10.2025
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%--    TODO поменять --%>title
    <title>Mathes</title>
</head>
<body>
<c:set var="currentPage" value="${matchesData.pageNumber}"/>
<c:set var="totalPages" value="${matchesData.totalPages}"/>


<!-- Первая страница -->
<c:if test="${currentPage > 1}">
    <a href="matches?page=1"><<</a>
</c:if>
<c:if test="${currentPage == 1}">
    <span><<</span>
</c:if>

<!-- Предыдущая страница -->
<c:if test="${currentPage > 1}">
    <a href="matches?page=${currentPage - 1}"><</a>
</c:if>
<c:if test="${currentPage == 1}">
    <span><<</span>
</c:if>

<!-- Текущая страница -->
<span>Страница ${currentPage} из ${totalPages}</span>

<!-- Следующая страница -->

<!-- Последняя страница -->

<%--
вытащить из дто и объявить jstl переменные
1) page из ссылки
2) всего страниц
пример как переменную объявлять     <c:set var="pageSize" value="${pageResult.pageSize}"/>


пример как ссылки объявлять
    <!-- Первая страница -->
    <c:if test="${currentPage > 1}">
        <a href="matches?page=1&size=${pageSize}">««</a>
    </c:if>
    <c:if test="${currentPage == 1}">
        <span>««</span>
    </c:if>

если page боль 1 тогда << с ссылкой на /matches=1. Если page == 1 тогда span, чтобы заблокировать
если page боль 1 тогда < с ссылкой на /matches=page-1. Если page == 1 тогда span, чтобы заблокировать

Текст "страница х из у"

если page < totalPages, тогда ссылка на /matches=page+1. Если page = totalpages, тогда span для блкировки
если page < totalPages, тогда ссылка на /matches=totalPages. Если page = totalpages, тогда span для блкировки


--%>
</body>
</html>
