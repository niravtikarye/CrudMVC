package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Service.JwtAuthFilter;
import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/problems")
public class ResolutionController {

    @Autowired
    private ProblemService problemService;

    private final String UPLOAD_DIR = "/tmp/civicsolve_uploads/";

    /**
     * Endpoint for a solver to mark a problem as RESOLVED by uploading an "After" image.
     */
    @PostMapping("/{probId}/solve")
    public ResponseEntity<String> solveProblem(
            @PathVariable Long probId,
            @RequestParam(value = "proofImage", required = false) MultipartFile proofImage,
            HttpServletRequest request) {

        try {
            // ✅ Read the user from the JWT request attribute (set by JwtAuthFilter)
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in.");
            }
            if ("citizen".equals(loggedInUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Citizens cannot solve problems.");
            }

            String savedImagePath = null;
            if (proofImage != null && !proofImage.isEmpty()) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String filename = System.currentTimeMillis() + "_" + proofImage.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + filename);
                proofImage.transferTo(dest);
                savedImagePath = "/uploads/" + filename;
            }

            problemService.markProblemSolved(probId, loggedInUser.getUserId(), savedImagePath);
            return ResponseEntity.ok("Problem marked as solved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error solving problem.");
        }
    }

    /**
     * Endpoint for the original citizen to VERIFY or RE_OPEN the problem.
     */
    @PostMapping("/{probId}/verify")
    public ResponseEntity<String> verifyProblem(
            @PathVariable Long probId,
            @RequestParam("status") String status,
            HttpServletRequest request) {

        try {
            // ✅ Read the user from the JWT request attribute (set by JwtAuthFilter)
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in.");
            }

            problemService.verifyProblem(probId, loggedInUser.getUserId(), status);
            return ResponseEntity.ok("Problem verification status updated.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing verification.");
        }
    }
}
