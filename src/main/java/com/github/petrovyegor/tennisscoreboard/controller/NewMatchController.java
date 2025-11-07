package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");
        validateGetRequestParameters(firstPlayerName, secondPlayerName);

        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        NewMatchResponseDto newMatchResponseDto = ongoingMatchesService.createOngoingMatch(newMatchRequestDto);
        response.sendRedirect("/match-score?uuid=%s".formatted(newMatchResponseDto.matchUuid()));
    }

    private void ensureNamesNotNullAndNotEmpty(String firstPlayerName, String secondPlayerName) {
        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)) {
            throw new InvalidParamException("Players names should not be null or empty");
        }
    }

    private void ensureNamesAreDifferent(String firstPlayerName, String secondPlayerName) {
        String name1 = firstPlayerName.trim();
        String name2 = secondPlayerName.trim();
        if (name1.equalsIgnoreCase(name2)) {
            throw new RestErrorException("Players names should not be equals");
        }
    }

    private void validateGetRequestParameters(String firstPlayerName, String secondPlayerName) {
        ensureNamesNotNullAndNotEmpty(firstPlayerName, secondPlayerName);
        ensureNamesAreDifferent(firstPlayerName, secondPlayerName);
    }

    private boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }
}
