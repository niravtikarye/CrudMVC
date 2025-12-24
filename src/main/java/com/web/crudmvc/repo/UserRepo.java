/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.crudmvc.repo;

import com.web.crudmvc.Database.Formbean.UserFormbean;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public class UserRepo {
       
 @Autowired
    private NamedParameterJdbcTemplate jdbc;

    // ================= VIEW =================
    public List<Map<String, Object>> showData() {

        StringBuilder query = new StringBuilder();
        query.append("SELECT user_id, uname, email, password, mobile, address ");
        query.append("FROM crud");

        return jdbc.queryForList(query.toString(), new MapSqlParameterSource());
    }
    
    public Map<String, Object> findById(int userId) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT user_id, uname, email, password, mobile, address ");
        query.append("FROM crud WHERE user_id = :userId");
        MapSqlParameterSource params =
                new MapSqlParameterSource("userId", userId);
        return jdbc.queryForList(query.toString(),params).getFirst();
    }

    // ================= INSERT =================
    public int insertData(UserFormbean form) {

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO crud(uname,email,password,mobile,address) ");
        query.append("VALUES(:name, :email, :password, :mobile, :address)");

        SqlParameterSource params = new BeanPropertySqlParameterSource(form);

        return jdbc.update(query.toString(), params);
    }

    // ================= UPDATE =================
    public int updateData(UserFormbean form) {

        StringBuilder query = new StringBuilder();
        query.append("UPDATE crud SET ");
        query.append("uname = :name, ");
        query.append("email = :email, ");
        query.append("password = :password, ");
        query.append("mobile = :mobile, ");
        query.append("address = :address ");
        query.append("WHERE user_id = :userId");

        SqlParameterSource params = new BeanPropertySqlParameterSource(form);

        return jdbc.update(query.toString(), params);
    }

    // ================= DELETE =================
    public int deleteData(UserFormbean form) {

        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM crud WHERE user_id = :userId");

        SqlParameterSource params = new BeanPropertySqlParameterSource(form);

        return jdbc.update(query.toString(), params);
    }
}
