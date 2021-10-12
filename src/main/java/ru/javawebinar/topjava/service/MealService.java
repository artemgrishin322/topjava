package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

public interface MealService {
    void add(Meal meal);
    void delete(int id);
    void update(Meal meal);
    Meal getById(int id);
}
