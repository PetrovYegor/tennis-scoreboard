<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New match</title>
</head>
<body>
<h1>New Match</h1>
<%--<p>${error}</p>--%>
<%--TODO добавить вывод ошибки в jsp, если валидация ругается--%>
<form method="post" action="/new-match">
    <label for="player1_name">Player 1</label>
    <input type="text" id="player1_name" name="player1_name"><br>

    <label for="player2_name">Player 2</label>
    <input type="text" id="player2_name" name="player2_name"><br>

    <button type="submit">Start</button>
</form>
</body>
</html>