<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<br>

<p>Player1: ${matchState.firstPlayerScore.playerName}</p>
<p>Sets: ${matchState.firstPlayerScore.sets}</p>
<p>Games: ${matchState.firstPlayerScore.games}</p>
<p>Point: ${matchState.firstPlayerScore.displayPoint}</p>

<br>
<br>

<p>Player2: ${matchState.secondPlayerScore.playerName}</p>
<p>Sets: ${matchState.secondPlayerScore.sets}</p>
<p>Games: ${matchState.secondPlayerScore.games}</p>
<p>Point: ${matchState.secondPlayerScore.displayPoint}</p>

<form method="post" action="/match-score">
    <input type="hidden" name="matchUuid" value="${matchState.matchUuid}">
    <input type="hidden" name="winnerId" value="${matchState.firstPlayerScore.playerId}">
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score">
    <input type="hidden" name="matchUuid" value="${matchState.matchUuid}">
    <input type="hidden" name="winnerId" value="${matchState.secondPlayerScore.playerId}">
    <button type="submit">Score</button>
</form>
</body>
</html>
