package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.ongoing_match.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidRequestException;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "MatchScoreController", urlPatterns = "/match-score")
public class MatchScoreController extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validateMatchScoreGetRequest(request);
        UUID matchUuid = UUID.fromString(request.getParameter("uuid"));//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
        OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);//Мб поменять всё-таки OngoingMatch Dto на MatchResponseDto
        request.setAttribute("matchState", ongoingMatchDto);

        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        validateMatchScorePostRequest(request);
        UUID matchUuid = UUID.fromString(request.getParameter("uuid"));

        if (!ongoingMatchesService.isOngoingMatchExists(matchUuid)) {
            response.sendRedirect("/matches?page=0");
            return;
        }

        int roundWinnerId = Integer.parseInt(request.getParameter("winnerId"));
        validateWinnerIdParameter(roundWinnerId);

        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuid, roundWinnerId);
        OngoingMatchDto ongoingMatchDto = matchScoreCalculationService.processAction(matchScoreRequestDto);

        if (ongoingMatchDto.isMatchFinished()) {
            finishedMatchesPersistenceService.processFinishedMatch(matchScoreRequestDto);
            request.setAttribute("matchUuid", matchUuid.toString());
            request.setAttribute("matchState", ongoingMatchDto);
            request.getRequestDispatcher("/finished-match.jsp").forward(request, response);
        }
        response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));
    }

    private void validateMatchScoreGetRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("uuid");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException("There is no match uuid parameter in the URL!");
        }
    }

    private void validateMatchScorePostRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("uuid", "winnerId");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException("Missing Match UUID or Winner ID or both");
        }
    }

    private void validateWinnerIdParameter(int roundWinnerId) {
        if (roundWinnerId <= 0) {
            throw new InvalidParamException("The round winner ID must be a positive number");
        }
    }
}
