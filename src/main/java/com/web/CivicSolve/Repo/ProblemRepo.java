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
        String sql = "INSERT INTO problems (user_id, subcategory_id, area_id, address_description, title, user_desc) "
                +
                "VALUES (:userId, :subcategoryId, :areaId, :addressDescription, :title, :userDesc)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", problem.getUserId())
                .addValue("subcategoryId", problem.getSubcategoryId())
                .addValue("areaId", problem.getAreaId())
                .addValue("addressDescription", problem.getAddressDescription())
                .addValue("title", problem.getTitle())
                .addValue("userDesc", problem.getUserDesc());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[] { "problem_id" });

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * Updates an existing problem.
     */
    public void updateProblem(Problem problem) {
        String sql = "UPDATE problems SET subcategory_id = :subcategoryId, area_id = :areaId, address_description = :addressDescription, title = :title, user_desc = :userDesc " +
                "WHERE problem_id = :probId AND user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", problem.getProbId())
                .addValue("userId", problem.getUserId())
                .addValue("subcategoryId", problem.getSubcategoryId())
                .addValue("areaId", problem.getAreaId())
                .addValue("addressDescription", problem.getAddressDescription())
                .addValue("title", problem.getTitle())
                .addValue("userDesc", problem.getUserDesc());

        jdbc.update(sql, params);
    }

    /**
     * Deletes a problem explicitly checking ownership.
     */
    public void deleteProblem(Long probId, Long userId) {
        // Manually tear down children records sequentially to assure foreign key constraint compatibility.
        jdbc.update("DELETE FROM problem_images WHERE problem_id = :probId", new MapSqlParameterSource("probId", probId));
        jdbc.update("DELETE FROM hype WHERE problem_id = :probId", new MapSqlParameterSource("probId", probId));
        jdbc.update("DELETE FROM problem_history WHERE problem_id = :probId", new MapSqlParameterSource("probId", probId));
        
        // Finally tear down parent row exclusively if caller is owner
        jdbc.update("DELETE FROM problems WHERE problem_id = :probId AND user_id = :userId", 
                new MapSqlParameterSource().addValue("probId", probId).addValue("userId", userId));
    }

    /**
     * Purges existing citizen images prior to a batch re-upload.
     */
    public void deleteCitizenImages(Long probId) {
        String sql = "DELETE FROM problem_images WHERE problem_id = :probId AND (image_type = 'before' OR image_type IS NULL)";
        jdbc.update(sql, new MapSqlParameterSource("probId", probId));
    }

    /**
     * Batch inserts image URLs into problem_images linked to a specific problem.
     * Deletes legacy old images prior to insertion.
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
                        dto.setUserId(rs.getLong("user_id"));
                        dto.setTitle(rs.getString("title"));
                        dto.setUserDesc(rs.getString("user_desc"));
                        dto.setSolverDesc(rs.getString("solver_desc"));
                        dto.setStatus(rs.getString("status"));
                        dto.setHypeCount(rs.getInt("hype_count"));
                        dto.setCreatedAt(rs.getTimestamp("created_at"));
                        dto.setAddressDescription(rs.getString("address_description"));
                        dto.setAuthorName(rs.getString("author_name"));
                        dto.setAreaName(rs.getString("area_name"));
                        dto.setAreaId(rs.getLong("area_id"));
                        dto.setSubcategoryName(rs.getString("subcategory_name"));
                        dto.setSubcategoryId(rs.getLong("subcategory_id"));
                        dto.setCategoryName(rs.getString("category_name"));
                        dto.setCategoryId(rs.getLong("category_id"));
                        // solver_id: null means ACTIVE/unassigned, non-null means already taken
                        long sid = rs.getLong("solver_id");
                        if (!rs.wasNull()) {
                            dto.setSolverId(sid);
                        }
                        map.put(probId, dto);
                    }
                    String imageUrl = rs.getString("image_url");
                    if (imageUrl != null) {
                        String imageType = rs.getString("image_type");
                        if (imageType == null || "before".equals(imageType)) {
                            dto.addCitizenImageUrl(imageUrl);
                        } else if ("after".equals(imageType)) {
                            dto.addSolverImageUrl(imageUrl);
                        }
                    }
                }
                return new ArrayList<>(map.values());
            }
        };
    }

    /**
     * Fetches all problems with their associations (user name, category, etc.) and
     * images attached for the feed.
     */
    public List<ProblemFeedDTO> getAllFeedProblems() {
        String sql = "SELECT p.problem_id, p.user_id, p.title, p.user_desc, p.solver_desc, p.status, p.hype_count, p.created_at, p.solver_id, p.address_description, "
                +
                "u.name AS author_name, " +
                "a.area_name, p.area_id, " +
                "sc.subcategory_name, p.subcategory_id, " +
                "c.category_name, sc.category_id, " +
                "pi.image_url, pi.image_type " +
                "FROM problems p " +
                "LEFT JOIN users u ON p.user_id = u.user_id " +
                "LEFT JOIN areas a ON p.area_id = a.area_id " +
                "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id "
                +
                "ORDER BY p.created_at DESC";

        return jdbc.query(sql, getFeedExtractor());
    }

    /**
     * Fetches problems filtered by Area, Category, and Status.
     */
    public List<ProblemFeedDTO> getFilteredFeedProblems(Long areaId, Long categoryId, String status) {
        StringBuilder sql = new StringBuilder(
                "SELECT p.problem_id, p.user_id, p.title, p.user_desc, p.solver_desc, p.status, p.hype_count, p.created_at, p.solver_id, p.address_description, "
                + "u.name AS author_name, "
                + "a.area_name, p.area_id, "
                + "sc.subcategory_name, p.subcategory_id, "
                + "c.category_name, sc.category_id, "
                + "pi.image_url, pi.image_type "
                + "FROM problems p "
                + "LEFT JOIN users u ON p.user_id = u.user_id "
                + "LEFT JOIN areas a ON p.area_id = a.area_id "
                + "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id "
                + "LEFT JOIN categories c ON sc.category_id = c.category_id "
                + "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id "
                + "WHERE 1=1 ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (areaId != null && areaId > 0) {
            sql.append(" AND p.area_id = :areaId ");
            params.addValue("areaId", areaId);
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND sc.category_id = :categoryId ");
            params.addValue("categoryId", categoryId);
        }
        if (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) {
            sql.append(" AND p.status = :status ");
            params.addValue("status", status);
        }

        sql.append(" ORDER BY p.created_at DESC");

        return jdbc.query(sql.toString(), params, getFeedExtractor());
    }

    /**
     * Fetches a specific problem by ID securely mapping all feed constraints identically.
     */
    public ProblemFeedDTO getProblemById(Long probId) {
        String sql = "SELECT p.problem_id, p.user_id, p.title, p.user_desc, p.solver_desc, p.status, p.hype_count, p.created_at, p.solver_id, p.address_description, "
                + "u.name AS author_name, " +
                "a.area_name, p.area_id, " +
                "sc.subcategory_name, p.subcategory_id, " +
                "c.category_name, sc.category_id, " +
                "pi.image_url, pi.image_type " +
                "FROM problems p " +
                "LEFT JOIN users u ON p.user_id = u.user_id " +
                "LEFT JOIN areas a ON p.area_id = a.area_id " +
                "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id "
                + "WHERE p.problem_id = :probId";

        MapSqlParameterSource params = new MapSqlParameterSource("probId", probId);
        List<ProblemFeedDTO> results = jdbc.query(sql, params, getFeedExtractor());
        return (results != null && !results.isEmpty()) ? results.get(0) : null;
    }

    /**
     * Fetches problems reported by a specific Citizen.
     */
    public List<ProblemFeedDTO> getProblemsByUserId(Long userId) {
        String sql = "SELECT p.problem_id, p.user_id, p.title, p.user_desc, p.solver_desc, p.status, p.hype_count, p.created_at, p.solver_id, p.address_description, "
                +
                "u.name AS author_name, " +
                "a.area_name, p.area_id, " +
                "sc.subcategory_name, p.subcategory_id, " +
                "c.category_name, sc.category_id, " +
                "pi.image_url, pi.image_type " +
                "FROM problems p " +
                "LEFT JOIN users u ON p.user_id = u.user_id " +
                "LEFT JOIN areas a ON p.area_id = a.area_id " +
                "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id "
                +
                "WHERE p.user_id = :userId " +
                "ORDER BY p.created_at DESC";

        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbc.query(sql, params, getFeedExtractor());
    }

    /**
     * Fetches problems currently assigned to a specific Solver (VMC/NGO/Noble).
     */
    public List<ProblemFeedDTO> getProblemsAssignedToUser(Long solverId) {
        String sql = "SELECT p.problem_id, p.user_id, p.title, p.user_desc, p.solver_desc, p.status, p.hype_count, p.created_at, p.solver_id, p.address_description, "
                +
                "u.name AS author_name, " +
                "a.area_name, p.area_id, " +
                "sc.subcategory_name, p.subcategory_id, " +
                "c.category_name, sc.category_id, " +
                "pi.image_url, pi.image_type " +
                "FROM problem_history ph " +
                "JOIN problems p ON ph.problem_id = p.problem_id " +
                "LEFT JOIN users u ON p.user_id = u.user_id " +
                "LEFT JOIN areas a ON p.area_id = a.area_id " +
                "LEFT JOIN sub_categories sc ON p.subcategory_id = sc.subcategory_id " +
                "LEFT JOIN categories c ON sc.category_id = c.category_id " +
                "LEFT JOIN problem_images pi ON p.problem_id = pi.problem_id "
                +
                "WHERE p.solver_id = :solverId " +
                "AND p.status IN ('IN_PROGRESS', 'RESOLVED', 'SOLVED', 'VERIFIED') " +
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
     * Because of our DB trigger fn_update_hype_count(), this will automatically
     * update problems.hype_count.
     */
    public void addHype(Long probId, Long userId) {
        String sql = "INSERT INTO hype (problem_id, user_id) VALUES (:probId, :userId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("userId", userId);
        jdbc.update(sql, params);
    }

    /**
     * Removes a hype record.
     * Because of our DB trigger fn_update_hype_count(), this will automatically
     * update problems.hype_count downwards.
     */
    public void removeHype(Long probId, Long userId) {
        String sql = "DELETE FROM hype WHERE problem_id = :probId AND user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("userId", userId);
        jdbc.update(sql, params);
    }

    /**
     * Executes the stored PL/pgSQL function assign_problem().
     */
    public void assignProblem(Long probId, Long solverId) {
        // 1. Run core procedure
        String sql = "SELECT assign_problem(:probId, :solverId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)
                .addValue("solverId", solverId);
        jdbc.query(sql, params, rs -> null);
    }

    /**
     * Executes the PL/pgSQL function to mark a problem as RESOLVED.
     */
    public void markProblemSolved(Long probId) {
        String sql = "SELECT mark_problem_solved(:probId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId);
        jdbc.query(sql, params, rs -> null);
    }

    /**
     * Executes the PL/pgSQL function to VERIFY or RE_OPEN a problem.
     */
    public void verifyProblem(Long probId, boolean status) {
        String sql = "SELECT verify_problem(:probId, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("probId", probId)             
                .addValue("status", status);
        jdbc.query(sql, params, rs -> null);
    }

    /**
     * Unassigns a problem, setting it back to 'PENDING'.
     */
    public void unassignProblem(Long probId) {
        String sql = "UPDATE problems SET solver_id = NULL, status = 'OPEN' WHERE problem_id = :probId";
        MapSqlParameterSource params = new MapSqlParameterSource("probId", probId);
        jdbc.update(sql, params);
    }

    /**
     * Automatically unassigns problems that have been stuck in IN_PROGRESS for too
     * long.
     * Uses a conservative estimate based on the created_at timestamp if assigned_at
     * is unavailable.
     */
    public int autoUnassignOverdueProblems() {
        String sql = "UPDATE problems SET solver_id = NULL, status = 'OPEN' " +
                "WHERE status = 'IN_PROGRESS' AND " +
                "CURRENT_TIMESTAMP - created_at > INTERVAL '48 hours'";
        return jdbc.update(sql, new MapSqlParameterSource());
    }

    /**
     * Updates the solver_desc column when a solver solves the problem.
     */
    public void updateSolverDescription(Long probId, String solverDesc) {
        String sql = "UPDATE problems SET solver_desc = :solverDesc WHERE problem_id = :probId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("solverDesc", solverDesc)
                .addValue("probId", probId);
        jdbc.update(sql, params);
    }
}
