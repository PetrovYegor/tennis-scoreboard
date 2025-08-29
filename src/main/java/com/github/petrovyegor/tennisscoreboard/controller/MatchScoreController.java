package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "MatchScoreController", urlPatterns = "/match-score")
public class MatchScoreController extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matchUuid = request.getParameter("uuid");
        request.setAttribute("matchUuid", matchUuid);
        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
        //по ид матча можно слазить в сервлет, хранящий матчи, и получить оттуда матч+ игроков + их счёт
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Post method received in MatchScoreController");
        int roundWinnerId = Integer.parseInt(request.getParameter("winnerId"));//сюда по идее валидацию, чтобы с постмана шлак не слать
        int firstPlayerId = Integer.parseInt(request.getParameter("firstPlayerId"));
        int secondPlayerId = Integer.parseInt(request.getParameter("secondPlayerId"));
        String stringMatchId = request.getParameter("matchUuid");
        UUID matchUuidId = UUID.fromString(stringMatchId);
        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuidId, firstPlayerId, secondPlayerId, roundWinnerId);
        matchScoreCalculationService.processAction(matchScoreRequestDto);
        response.sendRedirect("/match-score?uuid=%s".formatted(matchUuidId));
    }
}
