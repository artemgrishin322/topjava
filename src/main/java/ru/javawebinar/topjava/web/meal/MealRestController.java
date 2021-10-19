package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("get all meals");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), authUserCaloriesPerDay());
    }

    public Meal get(int mealId) {
        log.info("get meal {}", mealId);
        return service.get(mealId, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int mealId) {
        log.info("delete meal {}", mealId);
        service.delete(mealId, SecurityUtil.authUserId());
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update meal {}", meal);
        assureIdConsistent(meal, mealId);
        return service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getFilteredList(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (startDate == null) startDate = LocalDate.MIN;
        if (endDate == null) endDate = LocalDate.MAX;
        if (startTime == null) startTime = LocalTime.MIN;
        if (endTime == null) endTime = LocalTime.MAX;

        log.info("get filtered meals between {} and {}", startTime, endTime);
        return MealsUtil.getFilteredByDateTimeTos(service.getAll(authUserId()), authUserCaloriesPerDay(), startDate, endDate, startTime, endTime);
    }
 }