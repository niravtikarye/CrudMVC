package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Service.JwtAuthFilter;
import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/problems")
public class ProblemActionController {

    @Autowired
    private ProblemService problemService;

    /**
     * Endpoint to Hype (Upvote) a problem.
     * Reads identity from the JWT cookie (via JwtAuthFilter request attribute).
     */
    @PostMapping("/{probId}/hype")
    public ResponseEntity<String> hypeProblem(
            @PathVariable Long probId,
            HttpServletRequest request) {

        try {
            // ✅ Read the user from the JWT request attribute (set by JwtAuthFilter)
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to upvote.");
            }

            Long userId = loggedInUser.getUserId();
            boolean success = problemService.toggleHype(probId, userId);
            if (success) {
                return ResponseEntity.ok("Hype added successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User has already hyped this problem.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing hype.");
        }
    }

    /**
     * Endpoint to Assign a Solver to a problem.
     * Validates that the logged-in user is NOT a citizen before assigning.
     */
    @PostMapping("/{probId}/assign")
    public ResponseEntity<String> assignProblem(
            @PathVariable Long probId,
            @RequestParam Long solverId,
            @RequestParam Long assignedBy,
            HttpServletRequest request) {

        try {
            // ✅ Read the user from the JWT request attribute (set by JwtAuthFilter)
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to assign problems.");
            }
            if ("citizen".equals(loggedInUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Citizens cannot assign or solve problems.");
            }

            problemService.assignSolver(probId, solverId, assignedBy);
            return ResponseEntity.ok("Problem assigned successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning problem.");
        }
    }
}
