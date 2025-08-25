<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<br>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService" %>

<% OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();%>
    <% UUID matchUuid = UUID.fromString(((String)request.getAttribute("matchUuid"))); %>
<%    MatchScoreRequestDto matchScoreRequestDto = ongoingMatchesService.getGameState(matchUuid); %>
    <p>Player1: <%= matchScoreRequestDto.getFirstPlayerName()%></p>
    <p>Sets: <%=matchScoreRequestDto.getFirstPlayerSets()%></p>
    <p>Games:<%=matchScoreRequestDto.getFirstPlayerGames()%></p>
    <p>Point:<%=matchScoreRequestDto.getFirstPlayerPoint().getValue()%></p>
<br>
<br>
<p>Player2: <%= matchScoreRequestDto.getSecondPlayerName()%></p>
<p>Sets: <%=matchScoreRequestDto.getSecondPlayerSets()%></p>
<p>Games:<%=matchScoreRequestDto.getSecondPlayerGames()%></p>
<p>Point:<%=matchScoreRequestDto.getSecondPlayerPoint().getValue()%></p>

<form method="post" action="/match-score">
    <input type="hidden" name="playerId" value="player1">
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score">
    <input type="hidden" name="playerId" value="player2">
    <button type="submit">Score</button>
</form>


</body>
</html>
