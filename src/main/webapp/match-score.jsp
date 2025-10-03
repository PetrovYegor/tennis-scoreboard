<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<br>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto" %>
<%@ page import="java.util.UUID" %>

<%
    OngoingMatchDto ongoingMatchDto = (OngoingMatchDto) request.getAttribute("matchDisplayData");
    UUID matchUuid = ongoingMatchDto.getMatchUuid();

    int firstPlayerId = ongoingMatchDto.getFirstPlayerScore().getPlayerId();
    String firstPlayerName = ongoingMatchDto.getFirstPlayerScore().getPlayerName();
    int firstPlayerSets = ongoingMatchDto.getFirstPlayerScore().getSets();
    int firstPlayerGames = ongoingMatchDto.getFirstPlayerScore().getGames();
    String firstPlayerCurrentPoint = ongoingMatchDto.getFirstPlayerScore().getDisplayPoint();

    int secondPlayerId = ongoingMatchDto.getSecondPlayerScore().getPlayerId();
    String secondPlayerName = ongoingMatchDto.getSecondPlayerScore().getPlayerName();
    int secondPlayerSets = ongoingMatchDto.getSecondPlayerScore().getSets();
    int secondPlayerGames = ongoingMatchDto.getSecondPlayerScore().getGames();
    String secondPlayerCurrentPoint = ongoingMatchDto.getSecondPlayerScore().getDisplayPoint();
%>

<p>Player1: <%= firstPlayerName%></p>
<p>Sets: <%= firstPlayerSets%></p>
<p>Games:<%= firstPlayerGames%></p>
<p>Point:<%= firstPlayerCurrentPoint%></p>

<br>
<br>

<p>Player2: <%= secondPlayerName%></p>
<p>Sets: <%= secondPlayerSets%></p>
<p>Games:<%= secondPlayerGames%></p>
<p>Point:<%= secondPlayerCurrentPoint%></p>

<form method="post" action="/match-score">
    <input type="hidden" name="matchUuid" value=<%= matchUuid%>>
    <input type="hidden" name="winnerId" value=<%= firstPlayerId%>>
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score">
    <input type="hidden" name="matchUuid" value=<%= matchUuid%>>
    <input type="hidden" name="winnerId" value=<%= secondPlayerId%>>
    <button type="submit">Score</button>
</form>
</body>
</html>
