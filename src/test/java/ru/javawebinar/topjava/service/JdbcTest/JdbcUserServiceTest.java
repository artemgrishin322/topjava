package ru.javawebinar.topjava.service.JdbcTest;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = Profiles.JDBC)
public class JdbcUserServiceTest extends UserServiceTest {
}
