package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MealService {
    Meal create(Meal meal);
    void delete(int id);
    Meal update(Meal meal);
    Meal getById(int id);
    List<MealTo> getViewList();

    default int parseIdFromRequest(HttpServletRequest req) {
        return Integer.parseInt(req.getParameter("mealID"));
    }
}
