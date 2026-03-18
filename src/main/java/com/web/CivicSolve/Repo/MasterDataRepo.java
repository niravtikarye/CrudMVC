package com.web.CivicSolve.Repo;

import com.web.CivicSolve.Model.Area;
import com.web.CivicSolve.Model.Category;
import com.web.CivicSolve.Model.Organization;
import com.web.CivicSolve.Model.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MasterDataRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    // --- AREA ---
    public List<Area> getAllAreas() {
        String sql = "SELECT area_id, area_name, pincode FROM areas ORDER BY area_name ASC";
        return jdbc.query(sql, new RowMapper<Area>() {
            @Override
            public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Area(
                        rs.getLong("area_id"),
                        rs.getString("area_name"),
                        rs.getString("pincode")
                );
            }
        });
    }

    // --- CATEGORY ---
    public List<Category> getAllCategories() {
        String sql = "SELECT category_id, category_name FROM categories ORDER BY category_name ASC";
        return jdbc.query(sql, new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Category(
                        rs.getLong("category_id"),
                        rs.getString("category_name")
                );
            }
        });
    }

    // --- SUB-CATEGORY ---
    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId) {
        String sql = "SELECT subcategory_id, category_id, subcategory_name FROM sub_categories WHERE category_id = :categoryId ORDER BY subcategory_name ASC";
        MapSqlParameterSource params = new MapSqlParameterSource("categoryId", categoryId);
        return jdbc.query(sql, params, new RowMapper<SubCategory>() {
            @Override
            public SubCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new SubCategory(
                        rs.getLong("subcategory_id"),
                        rs.getLong("category_id"),
                        rs.getString("subcategory_name")
                );
            }
        });
    }

    // --- ORGANIZATION ---
    public List<Organization> getAllOrganizations() {
        String sql = "SELECT organization_id, organization_name, address, contact_number FROM organizations ORDER BY organization_name ASC";
        return jdbc.query(sql, new RowMapper<Organization>() {
            @Override
            public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Organization(
                        rs.getLong("organization_id"),
                        rs.getString("organization_name"),
                        rs.getString("address"),
                        rs.getString("contact_number")
                );
            }
        });
    }
}
