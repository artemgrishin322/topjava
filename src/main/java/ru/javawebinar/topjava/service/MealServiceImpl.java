package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServiceImpl implements MealService {
    private final AtomicInteger idCounter = new AtomicInteger();
    private final Map<Integer,Meal> meals = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.getMeals()) {
            create(meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        int id = idCounter.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
        } else throw new IllegalArgumentException("Illegal value of user id");

        return meal;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getMealList() {
        return new ArrayList<>(meals.values());
    }
}
