package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger meal_id = new AtomicInteger();

    public static int getId() {
        return meal_id.incrementAndGet();
    }
}
