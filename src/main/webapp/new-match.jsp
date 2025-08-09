<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New match</title>
</head>
<body>
<h1>New Match</h1>
<p>${error}</p>
<form method="post" action="/new-match">
    <label for="player1">Player 1</label>
    <input type="text" id="player1" name="player1"><br>

    <label for="player2">Player 2</label>
    <input type="text" id="player2" name="player2"><br>

    <button type="submit">Start</button>

</form>
</body>
</html>