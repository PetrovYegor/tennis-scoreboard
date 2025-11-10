<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>

<%--TODO добавить вывод в виде таблицы--%>
<%--TODO добавить сообщение ругающейся валидации--%>
<p>Player1: ${matchState.firstPlayerScore.playerName}</p>
<p>Sets: ${matchState.firstPlayerScore.sets}</p>
<p>Games: ${matchState.firstPlayerScore.games}</p>
<p>Point: ${matchState.firstPlayerScore.displayPoint}</p>

<p>Player2: ${matchState.secondPlayerScore.playerName}</p>
<p>Sets: ${matchState.secondPlayerScore.sets}</p>
<p>Games: ${matchState.secondPlayerScore.games}</p>
<p>Point: ${matchState.secondPlayerScore.displayPoint}</p>

<form method="post" action="/match-score?uuid=${matchState.matchUuid}">
<%--    TODO проверить, что можно удалить строку ниже--%>
<%--    <input type="hidden" name="matchUuid" value="${matchState.matchUuid}">--%>
    <input type="hidden" name="winnerId" value="${matchState.firstPlayerScore.playerId}">
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score?uuid=${matchState.matchUuid}">
<%--    <input type="hidden" name="matchUuid" value="${matchState.matchUuid}">--%>
    <input type="hidden" name="winnerId" value="${matchState.secondPlayerScore.playerId}">
    <button type="submit">Score</button>
</form>

</body>
</html>
