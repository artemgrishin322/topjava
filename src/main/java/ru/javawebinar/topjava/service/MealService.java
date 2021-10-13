package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal create(Meal meal);
    void delete(int id);
    Meal update(Meal meal);
    Meal getById(int id);
    List<Meal> getMealList();
}
