<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match-score</title>
</head>
<body>
<p>Helloooooooooooooooooooooooooooo</p>

<br>
<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto" %>
<%@ page import="com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService" %>
<%@ page import="java.util.UUID" %>

<% MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();%>
<%--    MatchScoreRequestDto matchScoreRequestDto = matchScoreCalculationService.getGameState((UUID.fromString((String)request.getAttribute("matchUuid"))); %>--%>
<%--    <p>Player1:</p> <%= matchScoreRequestDto.getFirstPlayerName()%>--%>
<%--    <p>Sets:</p> <%=matchScoreRequestDto.getFirstPlayerSets()%>--%>
<%--    <p>Games:</p> <%=matchScoreRequestDto.getFirstPlayerGames()%>--%>
<%--    <p>Point:</p> <%=matchScoreRequestDto.getFirstPlayerPoint()%>--%>
<br>
<br>



</body>
</html>
