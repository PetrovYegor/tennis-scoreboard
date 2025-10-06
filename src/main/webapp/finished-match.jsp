<%@ page import="com.github.petrovyegor.tennisscoreboard.dto.MatchScoreResponseDto" %><%--
  Created by IntelliJ IDEA.
  User: Yegor
  Date: 03.10.2025
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>privet, eto stranica s finalnim schetom</title>
</head>
<body>
<%
    MatchScoreResponseDto matchScoreResponseDto = (MatchScoreResponseDto) request.getAttribute("matchState");

    String firstPlayerName = matchScoreResponseDto.getOngoingMatchDto().getFirstPlayerScore().getPlayerName();
    String secondPlayerName = matchScoreResponseDto.getOngoingMatchDto().getSecondPlayerScore().getPlayerName();
    String winnerName = matchScoreResponseDto.getWinnerName();

    int firstPlayerSets = matchScoreResponseDto.getOngoingMatchDto().getFirstPlayerScore().getSets();
    int secondPlayerSets = matchScoreResponseDto.getOngoingMatchDto().getSecondPlayerScore().getSets();
%>

<p>winner: <%= winnerName%></p>
<p>player1: <%= firstPlayerName%></p>
<p>player1 sets: <%= firstPlayerSets%></p>
<p>player2:<%= secondPlayerName%></p>
<p>player2 sets:<%= secondPlayerSets%></p>
</body>
</html>
