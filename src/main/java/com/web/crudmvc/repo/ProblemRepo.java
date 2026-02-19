package com.web.crudmvc.repo;

import com.web.crudmvc.Database.Formbean.ProblemFormbean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Map<String, Object>> listAll() {
        String sql = "SELECT problem_id, title, description, created_by, assigned_to, status, created_at FROM problems ORDER BY created_at DESC";
        return jdbc.queryForList(sql, new MapSqlParameterSource());
    }

    public Map<String, Object> findById(int id) {
        String sql = "SELECT problem_id, title, description, created_by, assigned_to, status, created_at FROM problems WHERE problem_id = :id";
        return jdbc.queryForMap(sql, new MapSqlParameterSource("id", id));
    }

    public int insertProblem(ProblemFormbean form) {
        String sql = "INSERT INTO problems(title, description, created_by, status, created_at) VALUES(:title, :description, :createdBy, :status, now())";
        SqlParameterSource params = new BeanPropertySqlParameterSource(form);
        return jdbc.update(sql, params);
    }

    public int updateProblem(ProblemFormbean form) {
        String sql = "UPDATE problems SET title = :title, description = :description, assigned_to = :assignedTo, status = :status WHERE problem_id = :problemId";
        SqlParameterSource params = new BeanPropertySqlParameterSource(form);
        return jdbc.update(sql, params);
    }

    public int deleteProblem(int id) {
        String sql = "DELETE FROM problems WHERE problem_id = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    public int assignProblem(int id, int solverId) {
        String sql = "UPDATE problems SET assigned_to = :solverId, status = 'ASSIGNED' WHERE problem_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("solverId", solverId);
        return jdbc.update(sql, params);
    }
}
