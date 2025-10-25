package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "MatchScoreController", urlPatterns = "/match-score")
public class MatchScoreController extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        OngoingMatchDto ongoingMatchDto = (OngoingMatchDto) httpSession.getAttribute("matchState");

        if (ongoingMatchDto == null){
            UUID matchUuid = UUID.fromString(request.getParameter("uuid"));
            ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);
            request.setAttribute("matchState", ongoingMatchDto);
        }

        if (ongoingMatchDto.isMatchFinished()) {
            //не уверен, что при форварде всё ещё сохранится атрибут с именем победителя, мб нужно буднт повторно засунуть его
            getServletContext().getRequestDispatcher("/finished-match.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UUID matchUuid = UUID.fromString(request.getParameter("matchUuid"));
        int roundWinnerId = Integer.parseInt(request.getParameter("winnerId"));//сюда по идее валидацию, чтобы с постмана шлак не слать

        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuid, roundWinnerId);
        OngoingMatchDto ongoingMatchDto = matchScoreCalculationService.processAction(matchScoreRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("matchState", ongoingMatchDto);
//сохранение матча пока выключил
//        if (matchScoreResponseDto.isMatchFinished()){
//            finishedMatchesPersistenceService.processFinishedMatch(matchScoreRequestDto);
//        }

        response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));
    }
}
