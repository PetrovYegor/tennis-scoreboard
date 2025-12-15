<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/match-score.css">
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
        <h1 class="page-title">Match Score</h1>

        <div class="match-info">
            Match in progress: ${matchScore.firstPlayerScore.playerName} vs ${matchScore.secondPlayerScore.playerName}
        </div>

        <table class="table score-table">
            <thead>
            <tr>
                <th>PLAYER</th>
                <th>SETS</th>
                <th>GAMES</th>
                <th>POINTS</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="player-name">${matchScore.firstPlayerScore.playerName}</td>
                <td class="score-value">${matchScore.firstPlayerScore.sets}</td>
                <td class="score-value">${matchScore.firstPlayerScore.games}</td>
                <td class="score-value">${matchScore.firstPlayerScore.displayPoint}</td>
            </tr>
            <tr>
                <td class="player-name">${matchScore.secondPlayerScore.playerName}</td>
                <td class="score-value">${matchScore.secondPlayerScore.sets}</td>
                <td class="score-value">${matchScore.secondPlayerScore.games}</td>
                <td class="score-value">${matchScore.secondPlayerScore.displayPoint}</td>
            </tr>
            </tbody>
        </table>

        <div class="score-buttons-container">
            <div class="score-buttons">
                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${matchScore.matchUuid}" class="score-form">
                    <input type="hidden" name="winnerId" value="${matchScore.firstPlayerScore.playerId}">
                    <button type="submit" class="player-btn player1-btn">Player 1 Won Point</button>
                </form>

                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${matchScore.matchUuid}" class="score-form">
                    <input type="hidden" name="winnerId" value="${matchScore.secondPlayerScore.playerId}">
                    <button type="submit" class="player-btn player2-btn">Player 2 Won Point</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>