<%--
  Created by IntelliJ IDEA.
  User: Yegor
  Date: 28.10.2025
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--TODO Форма с фильтром по имени игрока. Поле ввода для имени и кнопка “искать”.
По нажатию формируется GET запрос вида /matches?filter_by_player_name=${NAME}--%>
<p>Privet</p>
<%--<p>Всего матчей ${matchesData.totalCount}</p>--%>

<c:choose>
    <c:when test="${matchesData.totalCount == '5'}">
        <p>Всего матчей ${matchesData.totalCount}</p>
    </c:when>
    <c:otherwise>
        <p>Всего матчей ${matchesData.totalCount}</p>
        <ul>
            <c:forEach var="match" items="${matchesData.content}">
                <li><c:out value="${match.firstPlayer.name} ${match.secondPlayer.name} ${match.winner.name}"/></li>

            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>



<%--<p>Всего матчей ${matchesData.firstPlayerName}</p>--%>
<%--<p>Всего матчей ${matchesData.secondPlayerName}</p>--%>
<%--<p>Всего матчей ${matchesData.winnerName}</p>--%>

<%--TODO Список найденных матчей--%>
<%--TODO Переключатель страниц, если матчей найдено больше, чем влезает на одну страницу--%>
</body>
</html>
