package com.web.CivicSolve.Service;

import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Repo.AuthRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;

    /**
     * Hashes the raw password and inserts the user into the database.
     */
    public void registerUser(UserDTO dto, String rawPassword) {
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));
        authRepo.registerUser(dto, hashedPassword);
    }

    /**
     * Validates credentials against the database hash.
     * Returns a populated UserDTO if successful, or null if login fails.
     */
    public UserDTO authenticate(String username, String rawPassword) {
        Map<String, Object> dbUser = authRepo.getUserByUsername(username);

        // Fail fast if user doesn't exist
        if (dbUser == null) {
            return null;
        }

        String storedHash = (String) dbUser.get("password");

        if (storedHash == null) {
            return null;
        }

        boolean passwordMatches = false;
        try {
            // Use BCrypt to safely compare the raw input with the stored hash
            if (BCrypt.checkpw(rawPassword, storedHash)) {
                passwordMatches = true;
            }
        } catch (IllegalArgumentException e) {
            // Fallback for plaintext passwords if hash is invalid
            if (storedHash.equals(rawPassword)) {
                passwordMatches = true;
            }
        }

        if (passwordMatches) {
            
            // Password matches! Construct the safe DTO for the session.
            UserDTO dto = new UserDTO();
            dto.setUserId(((Number) dbUser.get("user_id")).longValue());
            dto.setName((String) dbUser.get("name"));
            dto.setUsername((String) dbUser.get("username"));
            dto.setEmail((String) dbUser.get("email"));
            dto.setRole((String) dbUser.get("role"));
            
            Object orgIdObj = dbUser.get("organization_id");
            if (orgIdObj != null) {
                dto.setOrganizationId(((Number) orgIdObj).longValue());
            }

            return dto;
        }

        return null; // Password mismatch
    }
}
