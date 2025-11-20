<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%--    TODO поменять --%>
    <title>Mathes</title>
</head>
<body>
<%--<form method="get" action="/matches">--%>
<%--    <label for="filter_by_name">Search by name</label>--%>
<%--    <input type="text" id="filter_by_name" name="filter_by_name"><br>--%>

<%--    <button type="submit">Search</button>--%>
<%--</form>--%>


<c:set var="currentPage" value="${matchesData.pageNumber}"/>
<c:set var="totalPages" value="${matchesData.totalPages}"/>


<p>Всего матчей: ${matchesData.totalCount}</p>
<ul>
    <c:forEach var="match" items="${matchesData.content}">
    <li><c:out value="${match.firstPlayer.name} ${match.secondPlayer.name} ${match.winner.name}"/></li>
    </c:forEach>


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
    <c:if test="${currentPage < totalPages}">
    <a href="matches?page=${currentPage + 1}">></a>
    </c:if>
    <c:if test="${currentPage == totalPages}">
    <span>></span>
    </c:if>
    <!-- Последняя страница -->
    <c:if test="${currentPage < totalPages}">
    <a href="matches?page=${totalPages}">>></a>
    </c:if>
    <c:if test="${currentPage == totalPages}">
    <span>>></span>
    </c:if>

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
