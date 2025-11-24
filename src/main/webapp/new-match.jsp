<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Match</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/new-match.css">
</head>
<body>
<header class="header">
    <nav class="nav-container">
        <a href="index.jsp" class="nav-link">Home</a>
        <a href="http://localhost:8080/new-match" class="nav-link">New</a>
        <a href="http://localhost:8080/matches?page=1" class="nav-link">Matches</a>
    </nav>
</header>

<div class="container">
    <div class="content-box">
        <h1 class="page-title">New Match</h1>

        <!-- Error message placeholder -->
        <c:if test="${not empty error}">
            <div class="error-message">
                    ${error}
            </div>
        </c:if>

        <p class="form-instruction">
            Enter the names of both players to start a new tennis match
        </p>

        <form method="post" action="/new-match" class="new-match-form">
            <div class="form-row">
                <label for="player1_name" class="form-label">Player 1 Name:</label>
                <input type="text" id="player1_name" name="player1_name"
                       class="player-input" placeholder="Enter player 1 name"
                       required maxlength="50">
            </div>

            <div class="form-row">
                <label for="player2_name" class="form-label">Player 2 Name:</label>
                <input type="text" id="player2_name" name="player2_name"
                       class="player-input" placeholder="Enter player 2 name"
                       required maxlength="50">
            </div>

            <div class="submit-container">
                <button type="submit" class="submit-btn">START MATCH</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>