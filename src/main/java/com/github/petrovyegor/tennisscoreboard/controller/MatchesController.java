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

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidator.*;

@WebServlet(name = "MatchesController", urlPatterns = "/matches")
public class MatchesController extends HttpServlet {
    private static final int DEFAULT_PAGE_SIZE = 5;
    //TODO пробежаться по всем контроллерам и решить, как инициализировать сервисы (конструктор)?
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParameter = request.getParameter("page");
        String playerNameParameter = request.getParameter("filter_by_name");

        if (!isPageNotNullAndValid(pageParameter)) {
            response.sendRedirect("/matches?page=1");
            return;
        }

        if (!isNullOrEmpty(playerNameParameter)){
            if (!isNameValid(playerNameParameter)) {
                response.sendRedirect("/matches?page=1");
                return;
            }
        }

        int pageNumber = Integer.parseInt(pageParameter);
        MatchRequestDto matchRequestDto = MatchRequestDto.builder()
                .pageNumber(pageNumber)
                .pageSize(DEFAULT_PAGE_SIZE)
                .playerName(playerNameParameter)
                .build();

        PageResultDto pageResultDto = finishedMatchesPersistenceService.findMatches(matchRequestDto);
        if (isPageParameterMoreThenTotalPages(pageNumber, pageResultDto.getTotalPages())) {
            response.sendRedirect("/matches?page=1");
            return;
        }

        request.setAttribute("matchesData", pageResultDto);
        getServletContext().getRequestDispatcher("/matches.jsp").forward(request, response);
    }
}



