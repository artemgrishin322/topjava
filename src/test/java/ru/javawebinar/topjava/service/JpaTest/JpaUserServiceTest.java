package ru.javawebinar.topjava.service.JpaTest;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {
}
