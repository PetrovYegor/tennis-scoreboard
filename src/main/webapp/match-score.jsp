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
<%@ page import="com.github.petrovyegor.tennisscoreboard.model.MatchStatus" %>

<%
    OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    UUID matchUuid = UUID.fromString(((String) request.getAttribute("matchUuid")));
    OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);
    String firstPlayerName = ongoingMatchDto.getFirstPlayerScore().getPlayerName();
    int firstPlayerSets = ongoingMatchDto.getFirstPlayerScore().getSets();
    int firstPlayerGames = ongoingMatchDto.getFirstPlayerScore().getGames();

    String firstPlayerCurrentPoint;//ОСТАНОВИЛСЯ ТУТ. ПОПРОБОВАТЬ ВЕСЬ ДЖАВА КОД ВЫНЕСТИ НАВЕРХ И НОРМ ОТФОРМАТИРОВАТЬ, ПОКА БЕЗ ЕНУМА НА ОТДЕЛЬНЫЙ deuceScore и TIEBREAKSCORE. ЕСЛИ БУДЕТ ПРЯМ ЯВНАЯ НЕОБХОДИМОСТЬ, ТО СДЕЛАТЬ ЕНУМЫ, НО ощущение, ЧТО ЛУЧШЕ НЕ УСЛОЖНЯТЬ
    if (ongoingMatchDto.getMatchStatus() == MatchStatus.TIE_BREAK) {
        firstPlayerCurrentPoint = String.valueOf(ongoingMatchDto.getFirstPlayerScore().getTieBreakPoints());
    } else {
        firstPlayerCurrentPoint = ongoingMatchDto.getFirstPlayerPoint().getValue();
    }

    String secondPlayerName = ongoingMatchDto.getSecondPlayerScore().getPlayerName();
    int secondPlayerSets = ongoingMatchDto.getSecondPlayerScore().getSets();
    int secondPlayerGames = ongoingMatchDto.getSecondPlayerScore().getGames();

    String secondPlayerCurrentPoint;
    if (ongoingMatchDto.isHasAdvantageSecondPlayer()) {
        secondPlayerCurrentPoint = Point.ADVANTAGE.getValue();
    } else if (isTieBreak) {
        secondPlayerCurrentPoint = String.valueOf(ongoingMatchDto.getSecondPlayerTieBreakPoints());
    } else {
        secondPlayerCurrentPoint = ongoingMatchDto.getSecondPlayerPoint().getValue();
    }

%>
<p>Player1: <%= firstPlayerName%>
</p>
<p>Sets: <%= firstPlayerSets%>
</p>
<p>Games:<%=firstPlayerGames%>
</p>

<p>Point:<%= firstPlayerCurrentPoint%>
</p>
<br>
<br>
<p>Player2: <%= secondPlayerName%>
</p>
<p>Sets: <%=secondPlayerSets%>
</p>
<p>Games:<%=secondPlayerGames%>
</p>

<p>Point:<%=secondPlayerCurrentPoint%>
</p>

<form method="post" action="/match-score">

    <input type="hidden" name="firstPlayerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="secondPlayerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="winnerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="matchUuid" value=<%= request.getParameter("uuid")%>>
    <button type="submit">Score</button>
</form>

<form method="post" action="/match-score">
    <input type="hidden" name="firstPlayerId" value=<%= ongoingMatchDto.getFirstPlayerId()%>>
    <input type="hidden" name="secondPlayerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="winnerId" value=<%= ongoingMatchDto.getSecondPlayerId()%>>
    <input type="hidden" name="matchUuid" value=<%= request.getParameter("uuid")%>>
    <button type="submit">Score</button>
</form>


</body>
</html>
