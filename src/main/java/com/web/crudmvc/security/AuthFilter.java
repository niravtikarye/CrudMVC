package com.web.crudmvc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Allow OPTIONS (CORS preflight) without auth
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Jws<Claims> claims = JwtUtil.validateToken(token);
                Claims body = claims.getBody();

                Object uid = body.get("userId");
                Object role = body.get("role");

                if (uid != null) {
                    req.setAttribute("userId", Integer.parseInt(String.valueOf(uid)));
                }
                if (role != null) {
                    req.setAttribute("role", String.valueOf(role));
                }

            } catch (Exception ex) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Invalid or expired token");
                return;
            }
        } else {
            // No token provided: proceed but no user attributes set
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
