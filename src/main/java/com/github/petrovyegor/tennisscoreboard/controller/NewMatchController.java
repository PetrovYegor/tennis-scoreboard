package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Request recieved");
        String firstPlayerName = request.getParameter("player1");
        String secondPlayerName = request.getParameter("player2");
        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        var temp = request.getParameterMap();
        int a = 123;
        //логика по созданию матча через нужный сервис
        //потом уже редирект на другое вью
        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)){
            request.setAttribute("error", "One or both names are empty");
            doGet(request, response);
        } else {
            System.out.println("Match has been created");
        }

    }
    private boolean isNullOrEmpty(String source){
        return source == null || source.isEmpty();
    }
}
