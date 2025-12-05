<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/matches.css">
</head>
<body>
<header class="header">
    <nav class="nav-container">
        <a href="index.jsp" class="nav-link">Home</a>
        <a href="http://localhost:8080/new-match" class="nav-link">New</a>
        <a href="http://localhost:8080/matches?page=1" class="nav-link">Matches</a>
    </nav>
</header>

<div class="container">
    <div class="content-box">
        <h1 class="page-title">Finished Matches</h1>

        <c:set var="currentPage" value="${matchesData.pageNumber}"/>
        <c:set var="totalPages" value="${matchesData.totalPages}"/>
        <c:set var="filterValue" value="${param.filter_by_name}"/>
        <c:set var="filterParam" value=""/>
        <c:if test="${not empty filterValue}">
            <c:set var="filterParam" value="&filter_by_name=${fn:escapeXml(filterValue)}"/>
        </c:if>

        <div class="search-section">
            <form method="get" action="${pageContext.request.contextPath}/matches" class="search-form">
                <input type="hidden" name="page" value="1">
                <label for="filter_by_name" class="search-label">Name:</label>
                <input type="text" id="filter_by_name" name="filter_by_name"
                       value="${param.filter_by_name}" class="search-input"
                       placeholder="Enter player name to filter matches...">
                <div class="search-buttons">
                    <button type="submit" class="btn">Search</button>
                    <a href="${pageContext.request.contextPath}/matches?page=1" class="btn btn-secondary">Clear</a>
                </div>
            </form>
        </div>

        <c:choose>
            <c:when test="${not empty matchesData.content}">
                <table class="table matches-table">
                    <thead>
                    <tr>
                        <th class="id-cell">ID</th>
                        <th>Player 1</th>
                        <th>Player 2</th>
                        <th>Winner</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="match" items="${matchesData.content}">
                        <tr>
                            <td class="id-cell">${match.id}</td>
                            <td>${match.firstPlayer.name}</td>
                            <td>${match.secondPlayer.name}</td>
                            <td class="winner-cell">${match.winner.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="no-matches">
                    <i>No matches found</i><br>
                    <c:if test="${not empty param.filter_by_name}">
                        Try adjusting your search criteria
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="pagination-section">
            <div class="total-count">
                Total count: ${matchesData.totalCount}
            </div>

            <div class="pagination">
                <!-- First Page -->
                <c:if test="${currentPage > 1}">
                    <a href="matches?page=1${filterParam}">&lt;&lt;</a>
                </c:if>
                <c:if test="${currentPage == 1}">
                    <span>&lt;&lt;</span>
                </c:if>

                <!-- Previous Page -->
                <c:if test="${currentPage > 1}">
                    <a href="matches?page=${currentPage - 1}${filterParam}">&lt;</a>
                </c:if>
                <c:if test="${currentPage == 1}">
                    <span>&lt;</span>
                </c:if>

                <!-- Current Page Info -->
                <c:choose>
                    <c:when test="${totalPages == 0}">
                        <span class="page-info">Page ${currentPage}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="page-info">Page ${currentPage} of ${totalPages}</span>
                    </c:otherwise>
                </c:choose>

                <!-- Next Page -->
                <c:if test="${currentPage < totalPages}">
                    <a href="matches?page=${currentPage + 1}${filterParam}">&gt;</a>
                </c:if>
                <c:if test="${currentPage == totalPages}">
                    <span>&gt;</span>
                </c:if>

                <!-- Last Page -->
                <c:if test="${currentPage < totalPages}">
                    <a href="matches?page=${totalPages}${filterParam}">&gt;&gt;</a>
                </c:if>
                <c:if test="${currentPage == totalPages}">
                    <span>&gt;&gt;</span>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>