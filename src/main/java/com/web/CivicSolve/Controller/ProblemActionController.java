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
            String status = problemService.toggleHype(probId, userId);
            if ("added".equals(status)) {
                return ResponseEntity.ok("Hype added successfully.");
            } else {
                return ResponseEntity.ok("Hype removed successfully.");
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

            problemService.assignSolver(probId, solverId);
            return ResponseEntity.ok("Problem assigned successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning problem.");
        }
    }

    /**
     * Endpoint to Reject/Unassign a problem.
     */
    @PostMapping("/{probId}/unassign")
    public ResponseEntity<String> unassignProblem(
            @PathVariable Long probId,
            HttpServletRequest request) {

        try {
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("You must be logged in to modify assignments.");
            }
            if ("citizen".equals(loggedInUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Citizens cannot modify assignments.");
            }

            problemService.unassignProblem(probId);
            return ResponseEntity.ok("Problem rejected/unassigned successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error unassigning problem.");
        }
    }
}
