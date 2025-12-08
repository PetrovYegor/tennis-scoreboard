<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Tennis Score Board</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
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
  <div class="content-box">
    <h1 class="page-title">Tennis Score Board</h1>

    <p class="welcome-message">
      Welcome to the Tennis Score Board system. Manage matches, track scores,
      and view match history with ease.
    </p>

    <div class="links-container">
      <a href="${pageContext.request.contextPath}/new-match" class="feature-link">
        <span class="link-title">NEW MATCH</span>
        <span class="link-description">Start a new tennis match</span>
      </a>
      <a href="${pageContext.request.contextPath}/matches?page=1" class="feature-link">
        <span class="link-title">MATCHES</span>
        <span class="link-description">View finished matches history</span>
      </a>
    </div>
  </div>
</div>
</body>
</html>