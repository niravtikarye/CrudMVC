package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Service.AuthService;
import com.web.CivicSolve.Service.JwtAuthFilter;
import com.web.CivicSolve.Service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Authentication REST controller.
 *
 * Login  → issues a signed JWT stored in an HttpOnly cookie (auth_token).
 * Logout → clears the cookie by sending an expired replacement.
 *
 * Session is never used — the JWT cookie is the sole authentication vehicle.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    // -----------------------------------------------------------------------
    // POST /api/auth/register
    // -----------------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("name")     String name,
            @RequestParam("username") String username,
            @RequestParam("email")    String email,
            @RequestParam("password") String password,
            @RequestParam("role")     String role,
            @RequestParam(value = "organizationId", required = false) Long orgId) {

        try {
            UserDTO newUser = new UserDTO();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setRole(role);
            newUser.setOrganizationId(orgId);

            authService.registerUser(newUser, password);
            return ResponseEntity.ok("Registration successful. You can now log in.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed. Username or email might already exist.");
        }
    }

    // -----------------------------------------------------------------------
    // POST /api/auth/login
    // -----------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response) {

        UserDTO user = authService.authenticate(username, password);

        if (user != null) {
            // ✅ Credentials valid – generate JWT and store it in an HttpOnly cookie
            String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

            Cookie authCookie = new Cookie(JwtAuthFilter.COOKIE_NAME, token);
            authCookie.setHttpOnly(true);          // NOT readable by JS – XSS-safe
            authCookie.setPath("/");               // available to all paths
            authCookie.setMaxAge(24 * 60 * 60);    // 24 hours  (matches token expiry)
            // authCookie.setSecure(true);          // ← uncomment when running on HTTPS

            response.addCookie(authCookie);
            return ResponseEntity.ok("Login successful.");
        } else {
            // ❌ Bad credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        }
    }

    // -----------------------------------------------------------------------
    // GET /api/auth/logout
    // -----------------------------------------------------------------------
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response) {
        // Overwrite the existing cookie with an expired one so the browser deletes it
        Cookie expired = new Cookie(JwtAuthFilter.COOKIE_NAME, "");
        expired.setMaxAge(0);          // tells browser to delete immediately
        expired.setPath("/");
        expired.setHttpOnly(true);
        response.addCookie(expired);

        return ResponseEntity.ok("Logged out successfully.");
    }
}
