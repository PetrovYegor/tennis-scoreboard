<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<br>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.MatchScoreResponseDto" %>

<%boolean isMatchFinished = request.getAttribute("matchScoreDisplayData")%>



<% MatchScoreResponseDto matchScoreDisplayData = (MatchScoreResponseDto) request.getAttribute("matchScoreDisplayData");
    ;%>
<c:set var="isMatchFinished" value="<%= isMatchFinished%>"/>

<c:if test="${isMatchFinished == true}">
    <p>КОНЕЦ МАТЧА ёПТА</p>
</c:if>

<c:if test="${isMatchFinished == false}">
    String firstPlayerName = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getPlayerName();
    int firstPlayerSets = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getSets();
    <br>
    <br>
    String secondPlayerName = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getPlayerName();
    int secondPlayerSets = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getSets();


</c:if>

<%
    UUID matchUuid = matchScoreDisplayData.getOngoingMatchDto().getMatchUuid();

    int firstPlayerId = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getPlayerId();
    String firstPlayerName = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getPlayerName();
    int firstPlayerSets = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getSets();
    int firstPlayerGames = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getGames();
    String firstPlayerCurrentPoint = matchScoreDisplayData.getOngoingMatchDto().getFirstPlayerScore().getDisplayPoint();

    int secondPlayerId = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getPlayerId();
    String secondPlayerName = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getPlayerName();
    int secondPlayerSets = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getSets();
    int secondPlayerGames = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getGames();
    String secondPlayerCurrentPoint = matchScoreDisplayData.getOngoingMatchDto().getSecondPlayerScore().getDisplayPoint();
%>
<p>Player1: <%= firstPlayerName%>
</p>
<p>Sets: <%= firstPlayerSets%>
</p>
<p>Games:<%= firstPlayerGames%>
</p>
<p>Point:<%= firstPlayerCurrentPoint%>
</p>

<br>
<br>

<p>Player2: <%= secondPlayerName%>
</p>
<p>Sets: <%= secondPlayerSets%>
</p>
<p>Games:<%= secondPlayerGames%>
</p>
<p>Point:<%= secondPlayerCurrentPoint%>
</p>

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
