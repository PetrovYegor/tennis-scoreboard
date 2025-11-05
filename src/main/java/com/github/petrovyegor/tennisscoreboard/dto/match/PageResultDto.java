package com.github.petrovyegor.tennisscoreboard.dto.match;

import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResultDto {
    //сами данные, например лист матчей -- в репозитории посчитается
    private final List<Match> matches = new ArrayList<>();
    //счётчик записей, удовлетворяющих условию -- в репозитории посчитается
    private final long totalCount;
    //общее количество страниц -- в репозитории посчитаетя
    private final int totalPages;
    //количество элементов на одной странице -- где брать?
    private final int pageSize;
    //номер текущий страницы - можно с реквеста получить
    private final int pageNumber;
    //есть ли предыдущая страница
//    private final boolean hasPreviousPage;
//    //есть ли следующая страница
//    private final boolean hasNextPage;
//    //флаги первая ли страница
//    private final boolean isFirstPage;
//    // последняя ли страница
//    private final boolean isLastPage;
}
