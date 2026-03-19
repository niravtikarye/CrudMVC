package com.web.CivicSolve.Service;

import com.web.CivicSolve.Model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Reusable JWT utility.
 *
 * All secrets/config come from application.properties:
 * jwt.secret – HS256 signing key (min 32 chars)
 * jwt.expiry.ms – token lifetime in milliseconds (e.g. 86400000 = 24 h)
 *
 * Claims stored in every token:
 * sub – String(userId)
 * role – String (e.g. "citizen", "vmc")
 * exp – expiry timestamp
 */
@Component
@PropertySource("classpath:application.properties")
public class JwtUtil {

    // ── Secret and expiry injected from application.properties ─────────
    private final long expiryMs;

    /** Signing key – built in the constructor once properties are injected. */
    private final Key signingKey;

    /**
     * Spring calls this constructor and passes the @Value properties directly.
     * The signing key is built immediately — no @PostConstruct needed.
     */
    public JwtUtil(
            @Value("${jwt.secret}")    String secretString,
            @Value("${jwt.expiry.ms}") long expiryMs) {
        // Keys.hmacShaKeyFor requires the byte array to be ≥ 32 bytes for HS256
        this.signingKey = Keys.hmacShaKeyFor(secretString.getBytes());
        this.expiryMs   = expiryMs;
    }

    // ────────────────────────────────────────────────────────────────────
    // generateToken
    // ────────────────────────────────────────────────────────────────────

    /**
     * Creates a signed JWT containing userId, role, name, username, and email.
     *
     * @param user the authenticated UserDTO (must have all fields populated)
     * @return compact signed JWT string (header.payload.signature)
     */
    public String generateToken(UserDTO user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .claim("role",     user.getRole())
                .claim("name",     user.getName())
                .claim("username", user.getUsername())
                .claim("email",    user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ────────────────────────────────────────────────────────────────────
    // validateAndDecode
    // ────────────────────────────────────────────────────────────────────

    /**
     * Parses and verifies the JWT.
     *
     * @param token raw JWT string from the cookie
     * @return Claims object on success, or null if invalid/expired
     */
    public Claims validateAndDecode(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("[JwtUtil] Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("[JwtUtil] Invalid signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("[JwtUtil] Malformed token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("[JwtUtil] Unsupported token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[JwtUtil] Token parse error: " + e.getMessage());
        }
        return null;
    }

    // ────────────────────────────────────────────────────────────────────
    // getUserDTOFromToken (convenience wrapper used by JwtAuthFilter)
    // ────────────────────────────────────────────────────────────────────

    /**
     * Decodes the JWT and builds a lightweight {@link UserDTO} from it.
     *
     * @param token raw JWT string
     * @return populated UserDTO, or null if the token is invalid
     */
    public UserDTO getUserDTOFromToken(String token) {
        Claims claims = validateAndDecode(token);
        if (claims == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUserId(Long.parseLong(claims.getSubject()));
        dto.setRole(    (String) claims.get("role"));
        dto.setName(    (String) claims.get("name"));
        dto.setUsername((String) claims.get("username"));
        dto.setEmail(   (String) claims.get("email"));
        return dto;
    }
}
