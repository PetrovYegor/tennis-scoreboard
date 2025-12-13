package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match.MatchesRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.MatchesResponseDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidation.*;

@Slf4j
@WebServlet(name = "MatchesController", urlPatterns = "/matches")
public class MatchesController extends HttpServlet {
    private static final int DEFAULT_PAGE_SIZE = 5;
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!validateMatchesGetRequest(request, response)) {
            return;
        }
        String pageParameter = request.getParameter("page");
        String playerNameParameter = request.getParameter("filter_by_name");

        int pageNumber = Integer.parseInt(pageParameter);
        MatchesRequestDto matchesRequestDto = MatchesRequestDto.builder()
                .pageNumber(pageNumber)
                .pageSize(DEFAULT_PAGE_SIZE)
                .playerName(playerNameParameter)
                .build();

        MatchesResponseDto matchesResponseDto = finishedMatchesPersistenceService.findMatches(matchesRequestDto);
        if (isPageParameterMoreThenTotalPages(pageNumber, matchesResponseDto.pageNumber())) {
            response.sendRedirect("/matches?page=1");
            return;
        }

        request.setAttribute("matchesData", matchesResponseDto);
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
    }

    private boolean validateMatchesGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParameter = request.getParameter("page");
        String playerNameParameter = request.getParameter("filter_by_name");

        if (!isPageNotNullAndValid(pageParameter)) {
            log.warn("The received page number parameter is not valid '%s'".formatted(pageParameter));
            response.sendRedirect("/matches?page=1");
            return false;
        }
        if (!isNullOrEmpty(playerNameParameter)) {//TODO проверить валидность этой строки, внёс чугунной головй. Без неё не пятисостит т.к. имя пустое
            if (!isNameValid(playerNameParameter)) {
                log.warn("The received player name parameter is not valid '%s'".formatted(pageParameter));
                response.sendRedirect("/matches?page=1");
                return false;
            }
        }
        return true;
    }
}