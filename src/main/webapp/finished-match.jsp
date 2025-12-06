<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Match Finished</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/finished-match.css">
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
        <h1 class="page-title">Match Finished</h1>

        <div class="winner-message">
            Match finished: ${matchState.winnerName} wins!
        </div>

        <div class="match-summary">
            Final match result between ${matchState.firstPlayerScore.playerName}
            and ${matchState.secondPlayerScore.playerName}
        </div>

        <table class="table results-table">
            <thead>
            <tr>
                <th>PLAYER</th>
                <th>SETS</th>
            </tr>
            </thead>
            <tbody>
            <tr class="winner-row">
                <td class="player-name">${matchState.firstPlayerScore.playerName}</td>
                <td class="sets-value">${matchState.firstPlayerScore.sets}</td>
            </tr>
            <tr>
                <td class="player-name">${matchState.secondPlayerScore.playerName}</td>
                <td class="sets-value">${matchState.secondPlayerScore.sets}</td>
            </tr>
            </tbody>
        </table>

        <div class="home-btn-container">
            <a href="${pageContext.request.contextPath}/" class="home-btn">HOME</a>
            <a href="${pageContext.request.contextPath}/matches?page=1" class="home-btn">MATCHES</a>
        </div>
    </div>
</div>
</body>
</html>