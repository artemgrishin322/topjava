package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.concurrent.atomic.AtomicInteger;

public interface MealService {
    void add(Meal meal);
    void delete(AtomicInteger id);
    void update(MealTo meal);
    MealTo getById(AtomicInteger meal);
}
