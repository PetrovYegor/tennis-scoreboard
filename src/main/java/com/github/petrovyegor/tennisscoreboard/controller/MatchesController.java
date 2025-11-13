package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.match.MatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.PageResultDto;
import com.github.petrovyegor.tennisscoreboard.service.FinishedMatchesPersistenceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MatchesController", urlPatterns = "/matches")
public class MatchesController extends HttpServlet {
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
//        String pageParameter = request.getParameter("page");//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
//        String playerName = request.getParameter("filter_by_player_name");//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
//        int pageNumber;
//        if (pageParameter == null || pageParameter.isEmpty()){//нужна проверка на null и empty
//            pageNumber = 0;
//        } else {
//            pageNumber = Integer.parseInt(pageParameter);
//        }
//        MatchRequestDto matchRequestDto = MatchRequestDto.builder()
//                .pageNumber(pageNumber)
//                .playerName(playerName)
//                .build();
//
//        PageResultDto pageResultDto = finishedMatchesPersistenceService.findMatches(matchRequestDto);
//        request.setAttribute("matchesData", pageResultDto);
//        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
//
//        //Адрес - /matches?page=$page_number&filter_by_player_name=$player_name
//        //вероятно может быть без параметра фильтрации по имени, но номер страницы скорее всего есть всегда
//        //доставать из ссылки через getParametr значения, как в третьем проекте
//        //page - номер страницы. Если параметр не задан, подразумевается первая страница
//        //filter_by_player_name - имя игрока, матчи которого ищем. Если параметр не задан, отображаются все матчи

    }
}
