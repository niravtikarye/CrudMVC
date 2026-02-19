package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.Problem;
import com.web.CivicSolve.Model.UserInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Solve/api")
@CrossOrigin
public class ProblemApiController {

    private static final List<Problem> problems = new ArrayList<>();
    private static final List<UserInfo> users = new ArrayList<>();
    private static final AtomicInteger pid = new AtomicInteger(100);

    static {
        // dummy users
        UserInfo u1 = new UserInfo();
        u1.setUserId(1);
        u1.setName("Alice Citizen");
        u1.setEmail("alice@example.com");
        u1.setRole("CITIZEN");
        users.add(u1);

        UserInfo u2 = new UserInfo();
        u2.setUserId(2);
        u2.setName("Bob Solver");
        u2.setEmail("bob@example.com");
        u2.setRole("SOLVER");
        users.add(u2);

        // sample problems
        Problem p1 = new Problem();
        p1.setProblemId(pid.getAndIncrement());
        p1.setTitle("Pothole on 5th St");
        p1.setDescription("Large pothole near the crosswalk.");
        p1.setCreatedBy(1);
        p1.setAssignedTo(null);
        p1.setStatus("OPEN");
        p1.setCreatedAt(new Date());
        problems.add(p1);

        Problem p2 = new Problem();
        p2.setProblemId(pid.getAndIncrement());
        p2.setTitle("Street light not working");
        p2.setDescription("Corner street light is off at night.");
        p2.setCreatedBy(1);
        p2.setAssignedTo(2);
        p2.setStatus("ASSIGNED");
        p2.setCreatedAt(new Date());
        problems.add(p2);
    }

    @GetMapping("/problems")
    public List<Problem> list() {
        return problems;
    }

    @GetMapping("/problems/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<Problem> o = problems.stream().filter(p -> p.getProblemId() == id).findFirst();
        return o.isPresent() ? ResponseEntity.ok(o.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
    }

    @PostMapping("/problems")
    public ResponseEntity<?> create(@RequestBody Problem in, @RequestParam(value = "creatorId", required = false) Integer creatorId) {
        if (creatorId == null) {
            creatorId = 1; // default dummy user
        }
        in.setProblemId(pid.getAndIncrement());
        in.setCreatedBy(creatorId);
        in.setStatus("OPEN");
        in.setCreatedAt(new Date());
        problems.add(0, in);
        return ResponseEntity.status(HttpStatus.CREATED).body(in);
    }

    @PutMapping("/problems/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Problem in) {
        for (Problem p : problems) {
            if (p.getProblemId() == id) {
                if (in.getTitle() != null) p.setTitle(in.getTitle());
                if (in.getDescription() != null) p.setDescription(in.getDescription());
                if (in.getAssignedTo() != null) p.setAssignedTo(in.getAssignedTo());
                if (in.getStatus() != null) p.setStatus(in.getStatus());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
    }

    @DeleteMapping("/problems/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Iterator<Problem> it = problems.iterator();
        while (it.hasNext()) {
            if (it.next().getProblemId() == id) {
                it.remove();
                return ResponseEntity.ok("deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
    }

    @PostMapping("/problems/{id}/assign")
    public ResponseEntity<?> assign(@PathVariable int id, @RequestParam int solverId) {
        Optional<UserInfo> solver = users.stream().filter(u -> u.getUserId() == solverId && "SOLVER".equalsIgnoreCase(u.getRole())).findFirst();
        if (!solver.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid_solver");
        }
        for (Problem p : problems) {
            if (p.getProblemId() == id) {
                p.setAssignedTo(solverId);
                p.setStatus("ASSIGNED");
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found");
    }
}
