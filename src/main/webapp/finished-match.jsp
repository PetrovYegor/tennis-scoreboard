<%--
  Created by IntelliJ IDEA.
  User: Yegor
  Date: 03.10.2025
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%--    TODO поменять --%>title
    <title>privet, eto stranica s finalnim schetom</title>
</head>
<body>
<p>Match finished: ${matchState.winnerName} wins!</p>
<p>Player1: ${matchState.firstPlayerScore.playerName}</p>
<p>Sets: ${matchState.firstPlayerScore.sets}</p>
<p>Games: ${matchState.firstPlayerScore.games}</p>
<p>Player2: ${matchState.secondPlayerScore.playerName}</p>
<p>Sets: ${matchState.secondPlayerScore.sets}</p>
<p>Games: ${matchState.secondPlayerScore.games}</p>
</body>
</html>
