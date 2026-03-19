package com.web.CivicSolve.Service;

import com.web.CivicSolve.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet filter that runs on every request.
 *
 * Behaviour:
 *  - Reads the "auth_token" cookie.
 *  - If valid → decodes it into a UserDTO and stores it as
 *    request.setAttribute("loggedInUser", dto).
 *  - If missing or invalid → the attribute is not set (anonymous request).
 *  - Always forwards to the next filter / servlet  — this is NOT a blocking gate.
 *    Individual controllers / pages decide what to do with unauthenticated users.
 *
 * NOTE: Registered in web.xml via DelegatingFilterProxy (bean name = "jwtAuthFilter").
 *       Do NOT use @WebFilter — that creates a separate Tomcat-managed instance
 *       where @Autowired fields are null.
 */
@Component("jwtAuthFilter")
public class JwtAuthFilter implements Filter {

    /** Cookie name written by AuthController on login. */
    public static final String COOKIE_NAME = "auth_token";

    /** Request attribute key that controllers read. */
    public static final String USER_ATTR   = "loggedInUser";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String token = extractTokenFromCookies(httpReq);

        if (token != null) {
            UserDTO user = jwtUtil.getUserDTOFromToken(token);
            if (user != null) {
                // ✅ Valid JWT – make the user available for the whole request
                httpReq.setAttribute(USER_ATTR, user);
                System.out.println("[JwtAuthFilter] Authenticated userId="
                        + user.getUserId() + " role=" + user.getRole());
            } else {
                // ❌ Invalid / expired token – clear the stale cookie
                clearAuthCookie(httpResp);
                System.out.println("[JwtAuthFilter] Rejected invalid/expired token.");
            }
        }

        chain.doFilter(request, response);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Loops through request cookies and returns the value of "auth_token",
     * or null if the cookie is absent.
     */
    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Tells the browser to delete the auth_token cookie by setting maxAge=0.
     */
    private void clearAuthCookie(HttpServletResponse response) {
        Cookie expired = new Cookie(COOKIE_NAME, "");
        expired.setMaxAge(0);
        expired.setPath("/");
        expired.setHttpOnly(true);
        response.addCookie(expired);
    }

    @Override public void init(FilterConfig cfg) {}
    @Override public void destroy() {}
}
