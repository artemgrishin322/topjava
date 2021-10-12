package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private final MealService service;

    private final BiFunction<List<Meal>,Integer, List<MealTo>> filter = (meals, limit) -> {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .map(meal -> new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDate()) > limit))
                .collect(Collectors.toList());
    };

    public MealServlet() {
        super();
        this.service = new MealServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Redirect to meals");
        String action = req.getParameter("action").toLowerCase();
        switch (action) {
            case "delete" : {
                int mealId = Integer.parseInt(req.getParameter("mealID"));
                service.delete(mealId);
            }
            break;
            case "update" : {
                int mealId = Integer.parseInt(req.getParameter("mealID"));
                req.setAttribute("meal", service.getById(mealId));
                req.getRequestDispatcher("/mealCreation.jsp").forward(req, resp);
            }
            break;
            case "create" : {
                req.getRequestDispatcher("/mealCreation.jsp").forward(req, resp);
            }
        }

        req.setAttribute("meals", filter.apply(MealsUtil.getMeals(), MealsUtil.CALORIES_LIMIT));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = null;
        try {
            String dateTimeStr = req.getParameter("dateTime");
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (DateTimeParseException e) {
            log.debug("DateTimeParseException appeared", e);
        }

        String desc = req.getParameter("desc");
        int calories = Integer.parseInt(req.getParameter("cal"));
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            service.add(new Meal(dateTime, desc, calories));
        } else {
            Meal mealToBeUpdated = new Meal(dateTime, desc, calories);
            mealToBeUpdated.setId(Integer.parseInt(idStr));
            service.update(mealToBeUpdated);
        }

        doGet(req, resp);
    }
}
