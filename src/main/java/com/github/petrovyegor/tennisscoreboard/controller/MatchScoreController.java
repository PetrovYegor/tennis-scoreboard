package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.finished_match.MatchResultDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreResponseDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import com.github.petrovyegor.tennisscoreboard.service.PlayerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidation.*;

@Slf4j
@WebServlet(name = "MatchScoreController", urlPatterns = "/match-score")
public class MatchScoreController extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private final PlayerService playerService = new PlayerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!validateMatchScoreGetRequest(request, response)) {
            return;
        }

        String uuidParameter = request.getParameter("uuid");
        UUID matchUuid = UUID.fromString(uuidParameter);

        MatchScoreResponseDto matchScoreResponseDto = ongoingMatchesService.getMatchScore(matchUuid);
        request.setAttribute("matchScore", matchScoreResponseDto);

        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!validateMatchScorePostRequest(request, response)) {
            return;
        }

        String uuidParameter = request.getParameter("uuid");
        String matchWinnerIdParameter = request.getParameter("winnerId");

        UUID matchUuid = UUID.fromString(uuidParameter);
        int roundWinnerId = Integer.parseInt(matchWinnerIdParameter);

        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(matchUuid, roundWinnerId);
        MatchResultDto matchResultDto = matchScoreCalculationService.processAction(matchScoreRequestDto);

        if (matchResultDto.isMatchFinished()) {
            MatchScoreResponseDto matchScoreResponseDto = ongoingMatchesService.getMatchScore(matchUuid);
            finishedMatchesPersistenceService.processFinishedMatch(matchScoreRequestDto);
            request.setAttribute("matchUuid", matchUuid.toString());
            request.setAttribute("matchScore", matchScoreResponseDto);
            request.setAttribute("matchResult", matchResultDto);
            request.getRequestDispatcher("/finished-match.jsp").forward(request, response);
        }
        response.sendRedirect("/match-score?uuid=%s".formatted(matchUuid));
    }

    private boolean validateMatchScoreGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        if (isNullOrEmpty(uuidParameter)) {
            log.warn("The received match uuid parameter is null or empty '%s'".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Match ID is missing",
                    "Please provide a valid match ID in the URL");
            return false;
        }
        if (!isUUIDValid(uuidParameter)) {
            log.warn("The received match uuid parameter has incorrect format '%s'".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Invalid Match ID format",
                    "The provided match ID has incorrect format");
            return false;
        }

        UUID matchUuid = UUID.fromString(uuidParameter);
        if (!ongoingMatchesService.isOngoingMatchExist(matchUuid)) {
            log.warn("The ongoing match with uuid '%s' was not found".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Ongoing match not found",
                    "The ongoing match with ID '%s' was not found".formatted(uuidParameter));
            return false;
        }
        return true;
    }

    private boolean validateMatchScorePostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        String matchWinnerIdParameter = request.getParameter("winnerId");
        if (isNullOrEmpty(uuidParameter)) {
            log.warn("The received match uuid parameter is null or empty '%s'".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Match ID is missing",
                    "Please provide a valid match ID in the URL");
            return false;
        }

        if (!isUUIDValid(uuidParameter)) {
            log.warn("The received match uuid parameter has incorrect format '%s'".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Invalid Match ID format",
                    "The provided match ID has incorrect format");
            return false;
        }

        UUID matchUuid = UUID.fromString(uuidParameter);
        if (!ongoingMatchesService.isOngoingMatchExist(matchUuid)) {
            log.warn("The ongoing match with uuid '%s' was not found".formatted(uuidParameter));
            forwardToErrorPage(request, response,
                    "Ongoing match not found",
                    "The ongoing match with ID '%s' was not found".formatted(uuidParameter));
            return false;
        }

        if (isNullOrEmpty(matchWinnerIdParameter)) {
            log.warn("The player id parameter is null or empty '%s'".formatted(matchWinnerIdParameter));
            forwardToErrorPage(request, response,
                    "Winner ID is missing",
                    "Please provide a valid winner ID in the request payload");
            return false;
        }

        if (!isRoundWinnerIdValid(matchWinnerIdParameter)) {
            log.warn("The player id parameter has incorrect format '%s'".formatted(matchWinnerIdParameter));
            forwardToErrorPage(request, response,
                    "Invalid winner ID format",
                    "The provided winner ID has incorrect format");
            return false;
        }
        int roundWinnerId = Integer.parseInt(matchWinnerIdParameter);

        if (!playerService.isPlayerExist(roundWinnerId)) {
            log.warn("The player with id parameter '%s' does not exist".formatted(matchWinnerIdParameter));
            forwardToErrorPage(request, response,
                    "Player with ID does not exist",
                    "Please provide a valid winner ID in the request payload");
            return false;
        }
        return true;
    }

    private void forwardToErrorPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String message,
                                    String details) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorDetails", details);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}
