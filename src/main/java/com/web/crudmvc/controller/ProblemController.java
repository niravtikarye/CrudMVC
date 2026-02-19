package com.web.crudmvc.controller;

import com.web.crudmvc.Database.Formbean.ProblemFormbean;
import com.web.crudmvc.service.ProblemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService service;

    // Public: anyone can list problems
    @GetMapping("")
    public List<Map<String, Object>> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            Map<String, Object> p = service.findById(id);
            return ResponseEntity.ok(p);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
        }
    }

    // Create: only CITIZEN can create problems
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProblemFormbean form, HttpServletRequest req) {
        Object roleObj = req.getAttribute("role");
        Object uidObj = req.getAttribute("userId");
        if (roleObj == null || uidObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authentication_required");
        }
        String role = String.valueOf(roleObj);
        int uid = Integer.parseInt(String.valueOf(uidObj));
        if (!"CITIZEN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("only_citizen_can_create");
        }

        form.setCreatedBy(uid);
        form.setStatus("OPEN");
        service.create(form);
        Map<String, String> resp = new HashMap<>();
        resp.put("status", "created");
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // Update: only owner (creator) can update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProblemFormbean form, HttpServletRequest req) {
        Object uidObj = req.getAttribute("userId");
        if (uidObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authentication_required");
        }
        int uid = Integer.parseInt(String.valueOf(uidObj));

        try {
            Map<String, Object> existing = service.findById(id);
            int owner = Integer.parseInt(String.valueOf(existing.get("created_by")));
            if (owner != uid) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not_owner");
            }

            form.setProblemId(id);
            service.update(form);
            return ResponseEntity.ok("updated");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
        }
    }

    // Delete: only owner can delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, HttpServletRequest req) {
        Object uidObj = req.getAttribute("userId");
        if (uidObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authentication_required");
        }
        int uid = Integer.parseInt(String.valueOf(uidObj));

        try {
            Map<String, Object> existing = service.findById(id);
            int owner = Integer.parseInt(String.valueOf(existing.get("created_by")));
            if (owner != uid) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not_owner");
            }

            service.delete(id);
            return ResponseEntity.ok("deleted");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
        }
    }

    // Assign: only SOLVER can assign the problem to themselves
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assign(@PathVariable int id, HttpServletRequest req) {
        Object roleObj = req.getAttribute("role");
        Object uidObj = req.getAttribute("userId");
        if (roleObj == null || uidObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authentication_required");
        }
        String role = String.valueOf(roleObj);
        int uid = Integer.parseInt(String.valueOf(uidObj));
        if (!"SOLVER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("only_solver_can_assign");
        }

        try {
            service.assign(id, uid);
            return ResponseEntity.ok("assigned");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
        }
    }
}
