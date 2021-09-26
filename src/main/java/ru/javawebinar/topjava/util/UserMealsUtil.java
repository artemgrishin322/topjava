package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2019, Month.NOVEMBER, 26, 8, 0), "Завтрак", 650),
                new UserMeal(LocalDateTime.of(2019, Month.NOVEMBER, 26, 14, 0), "Обед", 1200),
                new UserMeal(LocalDateTime.of(2019, Month.NOVEMBER, 26, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2021, Month.MARCH, 15, 8, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2021, Month.MARCH, 15, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.MARCH, 15, 19, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2018, Month.JUNE, 2, 10, 0), "Завтрак", 1200),
                new UserMeal(LocalDateTime.of(2018, Month.JUNE, 2, 15, 0), "Обед", 600),
                new UserMeal(LocalDateTime.of(2018, Month.JUNE, 2, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2021, Month.OCTOBER, 6, 7, 0), "Завтрак", 850),
                new UserMeal(LocalDateTime.of(2021, Month.OCTOBER, 6, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.OCTOBER, 6, 18, 0), "Ужин", 215),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 18, 6, 30), "Завтрак", 650),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 18, 12, 0), "Обед", 980),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 18, 18, 15), "Ужин", 450)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println();
        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<LocalDate, Integer> caloriesToDays = new HashMap<>();
        List<UserMealWithExcess> resultList = new ArrayList<>(meals.size());

        for (UserMeal meal : meals) {
            LocalDate keyDate = meal.getDateTime().toLocalDate();

            caloriesToDays.put(keyDate, caloriesToDays.getOrDefault(keyDate, 0) + meal.getCalories());
        }

        for (UserMeal meal : meals) {
            if (!TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) continue;
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            boolean isExcess = caloriesToDays.get(mealDate) > caloriesPerDay;

            resultList.add(new UserMealWithExcess(meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    isExcess));
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesToDays = meals.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum));


        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesToDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
