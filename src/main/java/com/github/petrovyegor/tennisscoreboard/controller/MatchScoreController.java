package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match.MatchesRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.ongoing_match.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import com.github.petrovyegor.tennisscoreboard.service.PlayerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidation.*;

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

        OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);//Мб поменять всё-таки OngoingMatch Dto на MatchResponseDto
        request.setAttribute("matchState", ongoingMatchDto);

        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!validateMatchScorePostRequest(request, response)){
            return;
        }

        String uuidParameter = request.getParameter("uuid");
        String matchWinnerIdParameter = request.getParameter("winnerId");

        UUID matchUuid = UUID.fromString(uuidParameter);
        int roundWinnerId = Integer.parseInt(matchWinnerIdParameter);

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

    private boolean validateMatchScoreGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        if (isNullOrEmpty(uuidParameter)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Match ID is missing",
                    "Please provide a valid match ID in the URL");
            return false;
        }
        if (!isUUIDValid(uuidParameter)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Invalid Match ID format",
                    "The provided match ID has incorrect format");
            return false;
        }

        UUID matchUuid = UUID.fromString(uuidParameter);
        if (!ongoingMatchesService.isOngoingMatchExist(matchUuid)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Ongoing match not found",
                    "The ongoing match with ID '" + matchUuid + "' was not found");
            return false;
        }
        return true;
    }

    private boolean validateMatchScorePostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        String matchWinnerIdParameter = request.getParameter("winnerId");
        if (isNullOrEmpty(uuidParameter)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Match ID is missing",
                    "Please provide a valid match ID in the URL");
            return false;
        }

        if (!isUUIDValid(uuidParameter)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Invalid Match ID format",
                    "The provided match ID has incorrect format");
            return false;
        }

        UUID matchUuid = UUID.fromString(uuidParameter);
        if (!ongoingMatchesService.isOngoingMatchExist(matchUuid)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Ongoing match not found",
                    "The ongoing match with ID '" + matchUuid + "' was not found");
            return false;
        }

        if (isNullOrEmpty(matchWinnerIdParameter)) {//TODO проверить постманом
            forwardToErrorPage(request, response,
                    "Winner ID is missing",
                    "Please provide a valid winner ID in the request payload");
            return false;
        }

        if (!isRoundWinnerIdValid(matchWinnerIdParameter)) {
            forwardToErrorPage(request, response,
                    "Invalid winner ID format",
                    "The provided winner ID has incorrect format");
            return false;
        }
        int roundWinnerId = Integer.parseInt(matchWinnerIdParameter);

        if (!playerService.isPlayerExist(roundWinnerId)) {//TODO проверить постманом
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
