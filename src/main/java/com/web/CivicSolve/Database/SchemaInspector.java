package com.web.CivicSolve.Database;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Temp class to inspect target database tables related to problems.
 */
public class SchemaInspector {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DatabaseConfig.class);
        context.refresh();

        NamedParameterJdbcTemplate jdbc = context.getBean(NamedParameterJdbcTemplate.class);

        System.out.println("--- Table Inspection ---");

        String[] tables = {"problems", "problem_history", "hype"};

        for (String table : tables) {
            String sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = :tableName";
            try {
                List<Map<String, Object>> columns = jdbc.queryForList(sql, Map.of("tableName", table));
                System.out.println("\nTable: " + table);
                for (Map<String, Object> col : columns) {
                    System.out.println("  - " + col.get("column_name") + " (" + col.get("data_type") + ")");
                }
            } catch (Exception e) {
                System.out.println("Error reading " + table + ": " + e.getMessage());
            }
        }
        context.close();
    }
}
