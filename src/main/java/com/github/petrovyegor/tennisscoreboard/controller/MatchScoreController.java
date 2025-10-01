package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.RoundResultDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
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
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matchUuid = request.getParameter("uuid");
        request.setAttribute("matchUuid", matchUuid);
        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
        //по ид матча можно слазить в сервлет, хранящий матчи, и получить оттуда матч+ игроков + их счёт
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = UUID.fromString(request.getParameter("matchUuid"));
        int roundWinnerId = Integer.parseInt(request.getParameter("winnerId"));//сюда по идее валидацию, чтобы с постмана шлак не слать
        //OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);

        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuid, roundWinnerId);
        RoundResultDto roundResultDto = matchScoreCalculationService.processAction(matchScoreRequestDto);
        if (!roundResultDto.isMatchFinished()) {
            response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));//мб это в серви запихнуть?
            return;
        }
        finishedMatchesPersistenceService.processFinishedMatch(roundResultDto);
        response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid))
        //дописать код по сохранению матча в БД после окончания матча (проверить через UI h2, что сохраняется)
        //передать в сервис экземпляр оноинг матч, там создать объект матча и сохранить его через дао
        //Удаляем онгоингматч из коллекции текущих матчей
        //редиректим на страницу с результирующим счётом

    }
}
