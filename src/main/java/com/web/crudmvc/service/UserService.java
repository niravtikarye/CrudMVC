/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.crudmvc.service;

import com.web.crudmvc.Database.Formbean.UserFormbean;
import com.web.crudmvc.repo.UserRepo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author LENOVO
 */
@Service
public class UserService {
    
     @Autowired
    private UserRepo userRepo;

    // ============ VIEW ============
    public List<Map<String, Object>> getAllUsers() {
        return userRepo.showData();
    }
    
    public Map<String, Object> findById(int userId) {
        return userRepo.findById(userId);
    }
    
    // ============ INSERT ============
    public int saveUser(UserFormbean form) {
        return userRepo.insertData(form);
    }

    // ============ UPDATE ============
    public int updateUser(UserFormbean form) {
        return userRepo.updateData(form);
    }

    // ============ DELETE ============
    public int deleteUser(UserFormbean form) {
        return userRepo.deleteData(form);
    }
}
