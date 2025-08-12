package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.Match;
import com.github.petrovyegor.tennisscoreboard.service.NewMatchService;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParametersValidator.validateNewMatchPostRequest;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    private final NewMatchService newMatchService = new NewMatchService();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        validateNewMatchPostRequest(request);
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");
        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        Match newMatch = newMatchService.getNewMatch(newMatchRequestDto);
        ongoingMatchesService.addNewOngoingMatch(newMatch);
//        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)) {
//            request.setAttribute("error", "One or both names are empty");
//            doGet(request, response);
//        }
            response.sendRedirect("/match-score?uuid=%s".formatted(newMatch.getId()));
    }

    private boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }
}
