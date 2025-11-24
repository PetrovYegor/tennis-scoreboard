package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.ongoing_match.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
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

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidator.*;

@WebServlet(name = "MatchScoreController", urlPatterns = "/match-score")
public class MatchScoreController extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validateMatchScoreGetRequest(request);
        UUID matchUuid = UUID.fromString(request.getParameter("uuid"));//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
        if (!ongoingMatchesService.isOngoingMatchExists(matchUuid)) {
            response.sendRedirect("/matches?page=1");//TODO мб именно в гет методе сделать пересылку на страницу с ошибками, если что-то нет
            return;
        }
        OngoingMatchDto ongoingMatchDto = ongoingMatchesService.getMatchState(matchUuid);//Мб поменять всё-таки OngoingMatch Dto на MatchResponseDto
        request.setAttribute("matchState", ongoingMatchDto);

        getServletContext().getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        validateMatchScorePostRequest(request);
        UUID matchUuid = parseUuidParameter(request);

        if (!ongoingMatchesService.isOngoingMatchExists(matchUuid)) {
            response.sendRedirect("/matches?page=1");
            return;
        }

        int roundWinnerId = parseWinnerIdParameter(request);
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

    private int parseWinnerIdParameter(HttpServletRequest request) {
        String value = request.getParameter("winnerId");
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new InvalidParamException("Failed to parse the numeric value %s".formatted(value));//TODO проверить
        }
    }

    private UUID parseUuidParameter(HttpServletRequest request) {
        String value = request.getParameter("uuid");
        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new InvalidParamException("Failed to parse the UUID value %s".formatted(value));//TODO проверить
        }
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
