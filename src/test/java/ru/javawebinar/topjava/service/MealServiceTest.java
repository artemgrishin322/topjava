package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-app-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal userMeal = service.get(ID_TO_GET, USER_ID);
        assertMatch(userMeal, USER_MEAL2);
    }

    @Test
    public void delete() {
        service.delete(ID_TO_DELETE, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(ID_TO_DELETE, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(filtered, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> allUserMeals = service.getAll(USER_ID);
        List<Meal> allAdminMeals = service.getAll(ADMIN_ID);
        assertMatch(allUserMeals, USER_MEAL7, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);
        assertMatch(allAdminMeals, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void update() {
        Meal upd = getUpdated();
        service.update(upd, USER_ID);
        assertMatch(service.get(ID_TO_UPDATE, USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, DUPLICATE_DATE_TIME, "Duplicate", 111), USER_ID));
    }

    @Test
    public void getStranger() {
        assertThrows(NotFoundException.class, () -> service.get(STRANGER_ID, USER_ID));
    }

    @Test
    public void updateStranger() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void deleteStranger() {
        assertThrows(NotFoundException.class, () -> service.delete(STRANGER_ID, USER_ID));
    }
}