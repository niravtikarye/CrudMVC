package com.web.CivicSolve.Model;

/**
 * A safe Data Transfer Object representing a logged-in user.
 * Explicitly DOES NOT contain the user's password hash.
 */
public class UserDTO {
    private Long userId;
    private String name;
    private String username;
    private String email;
    private String role; // Stores the ENUM string representation (citizen, vmc, ngo, noble)
    private Long organizationId;

    public UserDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }
}
