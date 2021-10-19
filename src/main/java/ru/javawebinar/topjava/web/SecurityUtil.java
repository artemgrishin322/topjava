package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static int authorizedUser = 1;

    public static int authUserId() {
        return authorizedUser;
    }

    public static void setAuthorizedUser(int authorizedUser) {
        SecurityUtil.authorizedUser = authorizedUser;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}