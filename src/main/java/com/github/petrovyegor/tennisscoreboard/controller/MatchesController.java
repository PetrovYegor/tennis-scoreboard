package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match.MatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.PageResultDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MatchesController", urlPatterns = "/matches")
public class MatchesController extends HttpServlet {
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String pageParameter = request.getParameter("page");
        String playerName = request.getParameter("filter_by_player_name");
        //TODO добавить валидацию параметров страницы и имени
        int pageNumber = Integer.parseInt(pageParameter);
        MatchRequestDto matchRequestDto = MatchRequestDto.builder()
                .pageNumber(pageNumber)
                .playerName(playerName)
                .build();

        PageResultDto matchResponseDto = finishedMatchesPersistenceService.findMatches(matchRequestDto);

        //Адрес - /matches?page=$page_number&filter_by_player_name=$player_name
        //вероятно может быть без параметра фильтрации по имени, но номер страницы скорее всего есть всегда
        //доставать из ссылки через getParametr значения, как в третьем проекте
        //page - номер страницы. Если параметр не задан, подразумевается первая страница
        //filter_by_player_name - имя игрока, матчи которого ищем. Если параметр не задан, отображаются все матчи

    }
}
