package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
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
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = UUID.fromString(request.getParameter("uuid"));
        OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);
        request.setAttribute("matchDisplayData", ongoingMatchDto);
        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UUID matchUuid = UUID.fromString(request.getParameter("matchUuid"));
        int roundWinnerId = Integer.parseInt(request.getParameter("winnerId"));//сюда по идее валидацию, чтобы с постмана шлак не слать

        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuid, roundWinnerId);
        MatchScoreResponseDto matchScoreResponseDto = matchScoreCalculationService.processAction(matchScoreRequestDto);
        if (!matchScoreResponseDto.isMatchFinished()){
            finishedMatchesPersistenceService.processFinishedMatch(matchScoreRequestDto);
            //response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));
            request.setAttribute("finishedMatchData", matchScoreResponseDto);
            getServletContext().getRequestDispatcher("/finished-match.jsp").forward(request, response);
        }
        //response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));
        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }
}
