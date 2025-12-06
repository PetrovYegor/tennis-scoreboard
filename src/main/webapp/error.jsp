<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Tennis Score Board</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/error.css">
</head>
<body>
<header class="header">
    <nav class="nav-container">
        <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
        <a href="${pageContext.request.contextPath}/new-match" class="nav-link">New</a>
        <a href="${pageContext.request.contextPath}/matches?page=1" class="nav-link">Matches</a>
    </nav>
</header>

<div class="container">
    <div class="content-box error-box">
        <h1 class="page-title">Oops! Something went wrong</h1>

        <div class="error-icon">⚠️</div>

        <div class="error-message">
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    ${errorMessage}
                </c:when>
                <c:otherwise>
                    An unexpected error occurred. Please try again.
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${not empty errorDetails}">
            <div class="error-details">
                <strong>Details:</strong> ${errorDetails}
            </div>
        </c:if>

        <div class="action-buttons">
            <a href="index.jsp" class="btn btn-primary">Go Home</a>
            <a href="http://localhost:8080/matches?page=1" class="btn btn-secondary">View Matches</a>
        </div>
    </div>
</div>
</body>
</html>