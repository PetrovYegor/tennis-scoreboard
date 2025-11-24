<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <%--    TODO поменять --%>
    <title>Mathes</title>
</head>
<body>

<c:set var="currentPage" value="${matchesData.pageNumber}"/>
<c:set var="totalPages" value="${matchesData.totalPages}"/>
<!-- Создаем параметр фильтра для пагинации -->
<c:set var="filterValue" value="${param.filter_by_name}"/>
<c:set var="filterParam" value=""/>
<c:if test="${not empty filterValue}">
    <c:set var="filterParam" value="&filter_by_name=${fn:escapeXml(filterValue)}"/>
</c:if>


<form method="get" action="/matches">
    <input type="hidden" name="page" value="1">
    <label for="filter_by_name">Search by name</label>
    <input type="text" id="filter_by_name" name="filter_by_name" value="${param.filter_by_name}"><br>
    <button type="submit">Search</button>
</form>


<p>Всего матчей: ${matchesData.totalCount}</p>
<ul>
    <c:forEach var="match" items="${matchesData.content}">
    <li><c:out value="${match.firstPlayer.name} ${match.secondPlayer.name} ${match.winner.name}"/></li>
    </c:forEach>


    <!-- Первая страница -->
    <c:if test="${currentPage > 1}">
    <a href="matches?page=1${filterParam}"><<</a>
    </c:if>
    <c:if test="${currentPage == 1}">
    <span><<</span>
    </c:if>


    <!-- Предыдущая страница -->
    <c:if test="${currentPage > 1}">
    <a href="matches?page=${currentPage - 1}${filterParam}"><</a>
    </c:if>
    <c:if test="${currentPage == 1}">
    <span><<</span>
    </c:if>

    <!-- Текущая страница -->
    <span>Страница ${currentPage} из ${totalPages}</span>

    <!-- Следующая страница -->
    <c:if test="${currentPage < totalPages}">
    <a href="matches?page=${currentPage + 1}${filterParam}">></a>
    </c:if>
    <c:if test="${currentPage == totalPages}">
    <span>></span>
    </c:if>
    <!-- Последняя страница -->
    <c:if test="${currentPage < totalPages}">
    <a href="matches?page=${totalPages}${filterParam}">>></a>
    </c:if>
    <c:if test="${currentPage == totalPages}">
    <span>>></span>
    </c:if>

</body>
</html>
