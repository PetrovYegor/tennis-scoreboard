package com.github.petrovyegor.tennisscoreboard.controller;

import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "NewMatchController", urlPatterns = "/new-match")
public class NewMatchController extends HttpServlet {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/new-match.jsp").forward(request, response);
    }


/*
Проверяет существование игроков в таблице`Players`. Если игрока с таким именем не существует, создаём
			- Создаём экземпляр класса, содержащего айди игроков и текущий счёт, и кладём в коллекцию текущих матчей (существующую только в памяти приложения, либо в key-value storage). Ключом коллекции является UUID, значением - счёт в матче
			- Редирект на страницу`/match-score?uuid=$match_id`*
* */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstPlayerName = request.getParameter("player1_name");
        String secondPlayerName = request.getParameter("player2_name");
        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);
        Set<ConstraintViolation<NewMatchRequestDto>> violations = validator.validate(newMatchRequestDto);

        UUID tempMatchId = ongoingMatchesService.prepareNewMatch(newMatchRequestDto).tempMatchId();
        //Match newMatch = newMatchService.getNewMatch(newMatchRequestDto);
        //ongoingMatchesService.addNewOngoingMatch(newMatch);
//        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)) {
//            request.setAttribute("error", "One or both names are empty");
//            doGet(request, response);
//        }
        response.sendRedirect("/match-score?uuid=%s".formatted(tempMatchId));
    }

    private boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }

//    private void vaidatePostParameters(NewMatchRequestDto newMatchRequestDto) {
//        Set<ConstraintViolation<NewMatchRequestDto>> violations = validator.validate(newMatchRequestDto);
//        if (violations.size() != 0) {
//            throw new InvalidParamException();
//        }
//    }
}
