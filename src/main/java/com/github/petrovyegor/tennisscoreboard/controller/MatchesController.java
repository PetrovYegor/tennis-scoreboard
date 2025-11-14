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
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParameter = request.getParameter("page");
        String playerName = request.getParameter("filter_by_player_name");

        if (isNullOrEmpty(pageParameter)) {
            response.sendRedirect("/matches?page=0");
            return;
        }

        int pageNumber = Integer.parseInt(pageParameter);//TODO проверить постманом
        validatePageParameter(pageNumber);

        MatchRequestDto matchRequestDto = MatchRequestDto.builder()
                .pageNumber(pageNumber)
                .playerName(playerName)
                .build();

        PageResultDto pageResultDto = finishedMatchesPersistenceService.findMatches(matchRequestDto);
        request.setAttribute("matchesData", pageResultDto);
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
//
//        //Адрес - /matches?page=$page_number&filter_by_player_name=$player_name
//        //вероятно может быть без параметра фильтрации по имени, но номер страницы скорее всего есть всегда
//        //доставать из ссылки через getParametr значения, как в третьем проекте
//        //page - номер страницы. Если параметр не задан, подразумевается первая страница
//        //filter_by_player_name - имя игрока, матчи которого ищем. Если параметр не задан, отображаются все матчи
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
    }

    private void validateMatchesGetRequest(HttpServletRequest request) {//TODO проверить постманом
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("page");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException("There is no match page parameter in the URL!");
        }
    }

    private boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }

    private void validatePageParameter(int pageNumber) {
        if (pageNumber < 0) {
            throw new InvalidParamException("The page number must be a positive number");
        }
    }
}
