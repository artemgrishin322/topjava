package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServiceImpl implements MealService {
    @Override
    public void add(Meal meal) {
        MealsUtil.getMeals().add(meal);
    }

    @Override
    public void delete(AtomicInteger id) {

    }

    @Override
    public void update(MealTo meal) {

    }

    @Override
    public MealTo getById(AtomicInteger meal) {
        return null;
    }
}
