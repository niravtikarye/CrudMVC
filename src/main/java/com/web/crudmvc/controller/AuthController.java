package com.web.crudmvc.controller;

import com.web.crudmvc.repo.UserRepo;
import com.web.crudmvc.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Map<String, Object> user = userRepo.findByEmailAndPassword(email, password);
            int userId = Integer.parseInt(String.valueOf(user.get("user_id")));
            String role = String.valueOf(user.get("role"));
            String token = JwtUtil.generateToken(userId, email, role);

            Map<String, Object> resp = new HashMap<>();
            resp.put("token", token);
            resp.put("user", user);

            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "invalid_credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }
    }
}
