package com.web.CivicSolve.Repo;

import com.web.CivicSolve.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AuthRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * Registers a new user into the database.
     * Maps the String role to the PostgreSQL ENUM type using `CAST(:role AS user_role)`.
     */
    public void registerUser(UserDTO dto, String hashedPassword) {
        String sql = "INSERT INTO users (name, username, email, password, role, organization_id) " +
                     "VALUES (:name, :username, :email, :password, CAST(:role AS user_role), :orgId)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", dto.getName())
                .addValue("username", dto.getUsername())
                .addValue("email", dto.getEmail())
                .addValue("password", hashedPassword)
                .addValue("role", dto.getRole());

        // Handle the optional organization_id gracefully
        if (dto.getOrganizationId() != null && dto.getOrganizationId() > 0) {
             params.addValue("orgId", dto.getOrganizationId());
        } else {
             params.addValue("orgId", null);
        }

        jdbc.update(sql, params);
    }

    /**
     * Fetches a complete user row (including password hash) for validation.
     * Returns null if the username doesn't exist.
     */
    public Map<String, Object> getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("username", username);

        try {
            return jdbc.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Fetches a complete user row by ID.
     * Returns null if the user doesn't exist.
     */
    public Map<String, Object> getUserById(Long userId) {
        String sql = "SELECT * FROM users WHERE user_id = :userId LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);

        try {
            return jdbc.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
