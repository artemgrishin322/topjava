package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int ID_TO_DELETE = 100005;
    public static final int ID_TO_GET = 100003;
    public static final int ID_TO_UPDATE = 100004;
    public static final int STRANGER_ID = 100009;
    public static final int NOT_FOUND = 99999;
    public static final LocalDate START_DATE = LocalDate.of(2020, Month.JANUARY, 29);
    public static final LocalDate END_DATE = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDateTime DUPLICATE_DATE_TIME = LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0);

    public static final Meal USER_MEAL1 = new Meal(AbstractBaseEntity.START_SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(AbstractBaseEntity.START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(AbstractBaseEntity.START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(AbstractBaseEntity.START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal USER_MEAL5 = new Meal(AbstractBaseEntity.START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL6 = new Meal(AbstractBaseEntity.START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL7 = new Meal(AbstractBaseEntity.START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL1 = new Meal(AbstractBaseEntity.START_SEQ + 9, LocalDateTime.of(2021, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(AbstractBaseEntity.START_SEQ + 10, LocalDateTime.of(2021, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.OCTOBER, 27, 12, 0),
                "New meal", 1000);
    }

    public static Meal getUpdated() {
        Meal upd = new Meal(USER_MEAL3);
        upd.setDateTime(LocalDateTime.of(2000, Month.JANUARY, 1, 6, 0));
        upd.setDescription("Updated meal");
        upd.setCalories(322);
        return upd;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
