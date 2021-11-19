package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final ValidationHandler handler;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, ValidationHandler handler) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.handler = handler;
    }

    @Override
    @Transactional
    public User save(User user) {
        handler.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            int userId = newKey.intValue();
            insertOrUpdateRoles(user.getRoles(), userId, "create");
            user.setId(userId);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        } else if (Arrays.stream(insertOrUpdateRoles(user.getRoles(), user.id(), "update"))
                .anyMatch(value -> value == 0)) {
            return null;
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, string_agg(role, ', ') AS roles FROM users u
                LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE id=? GROUP BY id""", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, string_agg(role, ', ') AS roles FROM users u
                LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE email=? GROUP BY id""", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, string_agg(role, ', ') AS roles FROM users
                LEFT JOIN user_roles ur ON id = ur.user_id GROUP BY id ORDER BY max(name), max(email)""", ROW_MAPPER);
    }

    private int[] insertOrUpdateRoles(Set<Role> roles, int userId, String action) {
        if ("update".equals(action)) {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", userId);
        }

        List<Role> roleList = List.copyOf(roles);
        int[] updateCounts = jdbcTemplate.batchUpdate("INSERT INTO user_roles(role, user_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, roleList.get(i).toString());
                        ps.setInt(2, userId);
                    }

                    @Override
                    public int getBatchSize() {
                        return roleList.size();
                    }
                });

        return updateCounts;
    }
}
