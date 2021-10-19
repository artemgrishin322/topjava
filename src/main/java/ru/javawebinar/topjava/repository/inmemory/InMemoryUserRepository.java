package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer,User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(counter.incrementAndGet(), "admin", "admin@gmail.com", "ghp00Ap", Role.USER, Role.ADMIN));
        save(new User(counter.incrementAndGet(), "test_user", "test.user@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user", "test.user1@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user", "test.user2@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user", "test.user3@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user_Paul", "test.user.paul@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user_Michel", "test.user.michel@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user_Laurence", "test.user.laurence@gmail.com", "pjk86Pa*_ghtu", Role.USER));
        save(new User(counter.incrementAndGet(), "test_user_Kelly", "test.user.kelly@gmail.com", "pjk86Pa*_ghtu", Role.USER));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findAny().orElse(null);
    }
}
