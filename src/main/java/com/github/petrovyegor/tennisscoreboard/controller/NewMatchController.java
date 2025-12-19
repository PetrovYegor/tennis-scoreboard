package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidation.*;

@Slf4j
@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!validateNewMatchPostRequest(request, response)) {
            return;
        }
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");

        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        NewMatchResponseDto newMatchResponseDto = ongoingMatchesService.createOngoingMatch(newMatchRequestDto);
        response.sendRedirect("match-score?uuid=%s".formatted(newMatchResponseDto.matchUuid()));
    }

    private boolean validateNewMatchPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");

        if (isNullOrEmpty(firstPlayerName)) {
            log.warn("An invalid player name was received '%s'".formatted(firstPlayerName));
            forwardToNewMatchWithAttributes(request, response,
                    "First player name should not be null or empty.",
                    "The provided player name is incorrect");
            return false;
        }
        if (isNullOrEmpty(secondPlayerName)) {
            log.warn("An invalid player name was received '%s'".formatted(secondPlayerName));
            forwardToNewMatchWithAttributes(request, response,
                    "Second player name should not be null or empty.",
                    "The provided player name is incorrect");
            return false;
        }
        if (!isNameValid(firstPlayerName)) {
            log.warn("The received player name '%s' is not valid. Name length exceeded".formatted(firstPlayerName));
            forwardToNewMatchWithAttributes(request, response,
                    "The length of the player's name has been exceeded",
                    "The player's name must not exceed 50 characters in length");
            return false;
        }
        if (!isNameValid(secondPlayerName)) {
            log.warn("The received player name '%s' is not valid. Name length exceeded".formatted(secondPlayerName));
            forwardToNewMatchWithAttributes(request, response,
                    "The length of the player's name has been exceeded",
                    "The player's name must not exceed 50 characters in length");
            return false;
        }
        if (areNamesEquals(firstPlayerName, secondPlayerName)) {
            log.warn("The received names are equals. First player name '%s'. Second player name '%s'".formatted(firstPlayerName, secondPlayerName));
            forwardToNewMatchWithAttributes(request, response,
                    "The names of the players should not be equal",
                    "The provided names of the players match each other");
            return false;
        }
        return true;
    }

    private void forwardToNewMatchWithAttributes(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String message,
                                                 String details) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorDetails", details);
        request.getRequestDispatcher("/new-match.jsp").forward(request, response);
    }
}
