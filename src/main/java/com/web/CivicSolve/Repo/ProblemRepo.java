package com.web.CivicSolve.Repo;

import com.web.CivicSolve.Model.Problem;
import com.web.CivicSolve.Model.ProblemFeedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProblemRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * Inserts a new problem into the database and returns the generated problem_id.
     */
    public Long createProblem(Problem problem) {
        String sql = "INSERT INTO problems (user_id, subcategory_id, area_id, address_description, title, description) " +
                     "VALUES (:userId, :subcategoryId, :areaId, :addressDescription, :title, :description)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", problem.getUserId())
                .addValue("subcategoryId", problem.getSubcategoryId())
                .addValue("areaId", problem.getAreaId())
                .addValue("addressDescription", problem.getAddressDescription())
                .addValue("title", problem.getTitle())
                .addValue("description", problem.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"problem_id"});

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * Batch inserts image URLs into problem_images linked to a specific problem.
     */
    public void saveProblemImages(Long probId, List<String> imageUrls, String imageType) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO problem_images (problem_id, image_url, image_type) " +
                     "VALUES (:probId, :imageUrl, :imageType)";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[imageUrls.size()];
        for (int i = 0; i < imageUrls.size(); i++) {
            batchParams[i] = new MapSqlParameterSource()
                    .addValue("probId", probId)
                    .addValue("imageUrl", imageUrls.get(i))
                    .addValue("imageType", imageType);
        }

        jdbc.batchUpdate(sql, batchParams);
    }

    /**
     * Helper method containing the ResultSetExtractor to prevent code duplication.
     */
    private org.springframework.jdbc.core.ResultSetExtractor<List<ProblemFeedDTO>> getFeedExtractor() {
        return new org.springframework.jdbc.core.ResultSetExtractor<List<ProblemFeedDTO>>() {
            @Override
            public List<ProblemFeedDTO> extractData(ResultSet rs) throws SQLException {
                Map<Long, ProblemFeedDTO> map = new LinkedHashMap<>();
                while (rs.next()) {
                    Long probId = rs.getLong("problem_id");
                    ProblemFeedDTO dto = map.get(probId);
                    if (dto == null) {
                        dto = new ProblemFeedDTO();
                        dto.setProbId(probId);
                        dto.setTitle(rs.getString("title"));
                        dto.setDescription(rs.getString("description"));
                        dto.setStatus(rs.getString("status"));
                        dto.setHypeCount(rs.getInt("hype_count"));
                        dto.setCreatedAt(rs.getTimestamp("created_at"));
                        dto.setAuthorName(rs.getString("author_name"));
                        dto.setAreaName(rs.getString("area_name"));
                        dto.setSubcategoryName(rs.getString("subcategory_name"));
                        dto.setCategoryName(rs.getString("category_name"));
                        // solver_id: null means ACTIVE/unassigned, non-null means already taken
                        long sid = rs.getLong("solver_id");
                        if (!rs.wasNull()) {
                            dto.setSolverId(sid);
                        }
                        map.put(probId, dto);
                    }
                    String imageUrl = rs.getString("image_url");
                    if (imageUrl != null) {
                        dto.addImageUrl(imageUrl);
                    }
                }
                return new ArrayList<>(map.values());
            }
        };
    }

    /**
     * Fetches all problems with their associations (user name, category, etc.) and images attached for the feed.
     */
    public List<ProblemFeedDTO> getAllFeedProblems() {
        String sql = "SELECT p.problem_id, p.title, p.description, p.status, p.hype_count, p.created_at, p.solver_id, " +
                     "u.name AS author_name, " +
                     "a.area_name, " +
                     "sc.subcategory_name, " +
                     "c.category_name, " +
                     "pi.image_url " +
                     "FROM problems p " +
                     "LEFT JOIN users u ON p.user_id = u.user_id " +
                     "LEFT JOIN areas a ON p.area_id = a.area_id " +
                     "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                     "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                     "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id AND (pi.image_type IS NULL OR pi.image_type = 'before') " +
                     "ORDER BY p.created_at DESC";

        return jdbc.query(sql, getFeedExtractor());
    }

    /**
     * Fetches problems reported by a specific Citizen.
     */
    public List<ProblemFeedDTO> getProblemsByUserId(Long userId) {
        String sql = "SELECT p.problem_id, p.title, p.description, p.status, p.hype_count, p.created_at, p.solver_id, " +
                     "u.name AS author_name, " +
                     "a.area_name, " +
                     "sc.subcategory_name, " +
                     "c.category_name, " +
                     "pi.image_url " +
                     "FROM problems p " +
                     "LEFT JOIN users u ON p.user_id = u.user_id " +
                     "LEFT JOIN areas a ON p.area_id = a.area_id " +
                     "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                     "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                     "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id AND (pi.image_type IS NULL OR pi.image_type = 'before') " +
                     "WHERE p.user_id = :userId " +
                     "ORDER BY p.created_at DESC";

        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbc.query(sql, params, getFeedExtractor());
    }

    /**
     * Fetches problems currently assigned to a specific Solver (VMC/NGO/Noble).
     */
    public List<ProblemFeedDTO> getProblemsAssignedToUser(Long solverId) {
        String sql = "SELECT p.problem_id, p.title, p.description, p.status, p.hype_count, p.created_at, p.solver_id, " +
                     "u.name AS author_name, " +
                     "a.area_name, " +
                     "sc.subcategory_name, " +
                     "c.category_name, " +
                     "pi.image_url " +
                     "FROM problem_history ph " +
                     "JOIN problems p ON ph.problem_id = p.problem_id " +
                     "LEFT JOIN users u ON p.user_id = u.user_id " +
                     "LEFT JOIN areas a ON p.area_id = a.area_id " +
                     "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                     "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                     "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id AND (pi.image_type IS NULL OR pi.image_type = 'before') " +
                     "WHERE p.solver_id = :solverId " +
                     "AND p.status IN ('IN_PROGRESS', 'RESOLVED') " +
                     "ORDER BY p.created_at DESC";

        MapSqlParameterSource params = new MapSqlParameterSource("solverId", solverId);
        return jdbc.query(sql, params, getFeedExtractor());
    }

    /**
     * Checks if a user has already hyped a specific problem.
     * Returns true if a record exists, false otherwise.
     */
    public boolean checkUserHyped(Long probId, Long userId) {
        String sql = "SELECT COUNT(*) FROM hype WHERE problem_id = :probId AND user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("userId", userId);
        
        Integer count = jdbc.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    /**
     * Inserts a hype record. 
     * Because of our DB trigger fn_update_hype_count(), this will automatically update problems.hype_count.
     */
    public void addHype(Long probId, Long userId) {
        String sql = "INSERT INTO hype (problem_id, user_id) VALUES (:probId, :userId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("userId", userId);
        jdbc.update(sql, params);
    }

    /**
     * Executes the stored PL/pgSQL function assign_problem().
     */
    public void assignProblem(Long probId, Long solverId, Long assignedBy) {
        // 1. Run core procedure
        String sql = "SELECT assign_problem(:probId, :solverId, :assignedBy)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("solverId", solverId)
                .addValue("assignedBy", assignedBy);
        jdbc.query(sql, params, rs -> null);
    }

    /**
     * Executes the PL/pgSQL function to mark a problem as RESOLVED.
     */
    public void markProblemSolved(Long probId, Long solverId) {
        String sql = "SELECT mark_problem_solved(:probId, :solverId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("solverId", solverId);
        jdbc.query(sql, params, rs -> null);
    }

    /**
     * Executes the PL/pgSQL function to VERIFY or RE_OPEN a problem.
     */
    public void verifyProblem(Long probId, Long citizenId, String status) {
        String sql = "SELECT verify_problem(:probId, :citizenId, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("citizenId", citizenId)
                .addValue("status", status);
        jdbc.query(sql, params, rs -> null);
    }
}
