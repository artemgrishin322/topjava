package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealService service;

    @Override
    public void init() throws ServletException {
        service = new MealServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Redirect to meals");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action.toLowerCase()) {
                case "delete": {
                    log.debug("delete action");
                    int mealId = parseIdFromRequest(req);
                    service.delete(mealId);
                    resp.sendRedirect("meals");
                }
                return;
                case "update": {
                    log.debug("update action");
                    int mealId = parseIdFromRequest(req);
                    req.setAttribute("meal", service.getById(mealId));
                    req.getRequestDispatcher("/mealCreation.jsp").forward(req, resp);
                }
                break;
                case "create": {
                    log.debug("create action");
                    req.getRequestDispatcher("/mealCreation.jsp").forward(req, resp);
                }
            }
        }

        req.setAttribute("meals", MealsUtil.filteredByStreams(service.getMealList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_LIMIT));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime;
        try {
            String dateTimeStr = req.getParameter("dateTime");
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (DateTimeParseException e) {
            log.debug("DateTimeParseException appeared", e);
            throw new ServletException("Exception during date parse");
        }

        String desc = req.getParameter("desc");
        int calories = Integer.parseInt(req.getParameter("cal"));
        String idStr = req.getParameter("id");
        Meal newMeal = new Meal(dateTime, desc, calories);
        if (idStr == null || idStr.isEmpty()) {
            service.create(newMeal);
        } else {
            newMeal.setId(Integer.parseInt(idStr));
            service.update(newMeal);
        }

       resp.sendRedirect("meals");
    }

    private static int parseIdFromRequest(HttpServletRequest req) {
        return Integer.parseInt(req.getParameter("mealID"));
    }
}
