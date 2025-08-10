package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.github.petrovyegor.tennisscoreboard.util.RequestAndParametersValidator.validateNewMatchPostRequest;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        validateNewMatchPostRequest(request);
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");
        //каким-то образом сделать, чтобы если валидация ругалась, то вызывался бы doGet() и request.setAttribute("error", "One or both names are empty");
        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        //логика по созданию матча через нужный сервис
        //потом уже редирект на другое вью
        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)) {
            request.setAttribute("error", "One or both names are empty");
            doGet(request, response);
        } else {
            System.out.println("Match has been created");
        }

    }

    private boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }
}
