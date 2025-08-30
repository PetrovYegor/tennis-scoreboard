<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<br>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.model.Point" %>

<% OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();%>
    <% UUID matchUuid = UUID.fromString(((String)request.getAttribute("matchUuid"))); %>
<%    OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getGameState(matchUuid); %>
    <p>Player1: <%= ongoingMatchDto.getFirstPlayerName()%></p>
    <p>Sets: <%=ongoingMatchDto.getFirstPlayerSets()%></p>
    <p>Games:<%=ongoingMatchDto.getFirstPlayerGames()%></p>
    <% String firstPlayerCurrentPoint;
        if (ongoingMatchDto.isHasAdvantageFirstPlayer()){
            firstPlayerCurrentPoint = Point.ADVANTAGE.getValue();
        } else {
            firstPlayerCurrentPoint = ongoingMatchDto.getFirstPlayerPoint().getValue();
        }
    %>
    <p>Point:<%= firstPlayerCurrentPoint%></p>
<br>
<br>
<p>Player2: <%= ongoingMatchDto.getSecondPlayerName()%></p>
<p>Sets: <%=ongoingMatchDto.getSecondPlayerSets()%></p>
<p>Games:<%=ongoingMatchDto.getSecondPlayerGames()%></p>
<% String secondPlayerCurrentPoint;
    if (ongoingMatchDto.isHasAdvantageSecondPlayer()){
        secondPlayerCurrentPoint = Point.ADVANTAGE.getValue();
    } else {
        secondPlayerCurrentPoint = ongoingMatchDto.getSecondPlayerPoint().getValue();
    }
%>
<p>Point:<%=secondPlayerCurrentPoint%></p>

<form method="post" action="/match-score">

    <input type="hidden" name="firstPlayerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="secondPlayerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="winnerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="matchUuid" value=<%= request.getParameter("uuid")%> >
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score">
    <input type="hidden" name="firstPlayerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="secondPlayerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="winnerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="matchUuid" value=<%= request.getParameter("uuid")%> >
    <button type="submit">Score</button>
</form>


</body>
</html>
