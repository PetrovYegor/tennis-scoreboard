package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidator.validateNewMatchPostRequest;
import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParameterValidator.validateNewMatchPostRequestParameters;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateNewMatchPostRequest(request);
        String firstPlayerName = request.getParameter("player1_name");//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
        String secondPlayerName = request.getParameter("player2_name");//TODO проверить постманом, что валится ошибка. При необходимости добавить валидацию
        validateNewMatchPostRequestParameters(firstPlayerName, secondPlayerName);//нужна валидация на длину имени

        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        NewMatchResponseDto newMatchResponseDto = ongoingMatchesService.createOngoingMatch(newMatchRequestDto);
        response.sendRedirect("/match-score?uuid=%s".formatted(newMatchResponseDto.matchUuid()));
    }
}
