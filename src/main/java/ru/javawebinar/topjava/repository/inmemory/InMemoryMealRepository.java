package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 8, 8, 0), "Завтрак юзера 2", 610), 2);
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 8, 14, 0), "Обед юзера 2", 800), 2);
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 8, 19, 0), "Ужин юзера 2", 600), 2);
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 12, 8, 0), "Завтрак юзера 2", 610), 2);
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 12, 14, 0), "Обед юзера 2", 800), 2);
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 12, 19, 0), "Ужин юзера 2", 600), 2);
        save(new Meal(LocalDateTime.of(2020, Month.JUNE, 26, 8, 0), "Завтрак юзера 2", 610), 2);
        save(new Meal(LocalDateTime.of(2020, Month.JUNE, 26, 14, 0), "Обед юзера 2", 800), 2);
        save(new Meal(LocalDateTime.of(2020, Month.JUNE, 26, 19, 0), "Ужин юзера 2", 600), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, id -> new ConcurrentHashMap<>());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.remove(mealId) != null;
        }
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.get(mealId);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.values().stream()
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return repository.get(userId)
                    .values()
                    .stream()
                    .filter(meal -> DateTimeUtil.isBetweenClosed(meal.getDate(), startDate, endDate))
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}

