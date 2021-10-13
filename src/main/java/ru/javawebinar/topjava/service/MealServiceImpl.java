package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServiceImpl implements MealService {
    private static final AtomicInteger id_counter = new AtomicInteger();
    private final Map<Integer,Meal> meals = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.getMeals()) {
            int id = id_counter.incrementAndGet();
            meal.setId(id);
            meals.put(id, meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        int id = id_counter.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);

        return meal;
    }

    @Override
    public void delete(int id) {
        id_counter.decrementAndGet();
        meals.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        meals.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public List<MealTo> getViewList() {
        return MealsUtil.filteredByStreams(getAllMealsList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_LIMIT);
    }

    private List<Meal> getAllMealsList() {
        return new ArrayList<>(meals.values());
    }
}
