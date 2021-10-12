package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServiceImpl implements MealService {
    @Override
    public void add(Meal meal) {
        MealsUtil.getMeals().add(meal);
    }

    @Override
    public void delete(int id) {
        MealsUtil.getMeals().removeIf(meal -> meal.getId() == id);
    }


    @Override
    public void update(Meal meal) {
        for (Meal oldMeal : MealsUtil.getMeals()) {
            if (meal.getId() == oldMeal.getId()) {
                oldMeal.setDateTime(meal.getDateTime());
                oldMeal.setDescription(meal.getDescription());
                oldMeal.setCalories(meal.getCalories());
            }
        }
    }

    @Override
    public Meal getById(int id) {
        Meal foundMeal = null;
        for (Meal meal : MealsUtil.getMeals()) {
            if (meal.getId() == id) {
                foundMeal = meal;
                break;
            }
        }

        return foundMeal;
    }
}
