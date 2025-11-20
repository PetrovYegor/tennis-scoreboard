package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match.MatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.PageResultDto;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidRequestException;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "MatchesController", urlPatterns = "/matches")
public class MatchesController extends HttpServlet {
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_MAX_PAGE_NUMBER = 100;
    private static final String DEFAULT_FIRST_PAGE_URL = "/matches?page=1";
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validateMatchesGetRequest(request);//TODO проверить

        String pageParameter = request.getParameter("page");//обязательно нужна валидация на значение странцы
        String playerNameParameter = request.getParameter("filter_by_player_name");//TODO обязательно нужна валидация на длину имени

        int pageNumber = parsePageParameter(pageParameter);//TODO проверить
        validatePageNumber(pageNumber);//TODO проверить
        //учитывая все проверки выше, у в url всегда будет параметр page

        MatchRequestDto matchRequestDto = MatchRequestDto.builder()
                .pageNumber(pageNumber)
                .pageSize(DEFAULT_PAGE_SIZE)
                .playerName(playerNameParameter)
                .build();

        PageResultDto pageResultDto = finishedMatchesPersistenceService.findMatches(matchRequestDto);
        ensurePageParameterLessThenTotalPages(pageNumber, pageResultDto.getTotalPages());

        request.setAttribute("matchesData", pageResultDto);
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
    }

    private int parsePageParameter(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new InvalidParamException("Failed to parse the page number value %s".formatted(value));//TODO проверить
        }
    }

//    private boolean isNullOrEmpty(String source) {
//        return source == null || source.trim().isEmpty();
//    }

    private void validateMatchesGetRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("page");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException("There is no page parameter in the URL!");
        }
    }

    private void validatePageNumber(int pageNumber) {
        if (pageNumber < 1) {
            throw new InvalidParamException("The page number be a positive number");
        }
        if (pageNumber >= DEFAULT_MAX_PAGE_NUMBER) {
            throw new InvalidParamException("The page number cannot exceed ".formatted(DEFAULT_MAX_PAGE_NUMBER));
        }
    }

    private void ensurePageParameterLessThenTotalPages(int pageFromRequest, int totalPages) {
        if (pageFromRequest > totalPages && totalPages > 0) {
            throw new InvalidParamException("This page number does not exist.");
        }
    }
}



