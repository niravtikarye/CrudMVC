package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.ProblemFeedDTO;
import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Service.JwtAuthFilter;
import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/profile")
    public ModelAndView showProfile(HttpServletRequest request) {
        // ✅ Read the user decoded from the JWT cookie by JwtAuthFilter
        UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);

        // If not logged in, redirect to login page
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user", loggedInUser);

        // Render different lists based on role
        if ("citizen".equals(loggedInUser.getRole())) {
            List<ProblemFeedDTO> problems = problemService.getProblemsByUserId(loggedInUser.getUserId());
            
            List<ProblemFeedDTO> pendingProblems = problems.stream()
                    .filter(p -> p.getSolverId() == null && ("OPEN".equalsIgnoreCase(p.getStatus()) || "REOPENED".equalsIgnoreCase(p.getStatus()) || p.getStatus() == null))
                    .collect(Collectors.toList());

            List<ProblemFeedDTO> assignedProblems = problems.stream()
                    .filter(p -> "IN_PROGRESS".equalsIgnoreCase(p.getStatus()))
                    .collect(Collectors.toList());

            List<ProblemFeedDTO> solvedProblems = problems.stream()
                    .filter(p -> "SOLVED".equalsIgnoreCase(p.getStatus()) || "RESOLVED".equalsIgnoreCase(p.getStatus()) || "VERIFIED".equalsIgnoreCase(p.getStatus()))
                    .collect(Collectors.toList());

            mv.addObject("feedType", "My Reported Issues");
            mv.addObject("pendingProblems", pendingProblems);
            mv.addObject("assignedProblems", assignedProblems);
            mv.addObject("solvedProblems", solvedProblems);
        } else {
            // Solver
            mv.addObject("feedType", "Problem Dashboard");

            // 1. Available Problems (All unassigned problems in feed)
            List<ProblemFeedDTO> allFeed = problemService.getAllFeedProblems();
            List<ProblemFeedDTO> availableProblems = allFeed.stream()
                    .filter(p -> p.getSolverId() == null && ("OPEN".equalsIgnoreCase(p.getStatus()) || "REOPENED".equalsIgnoreCase(p.getStatus()) || p.getStatus() == null))
                    .collect(Collectors.toList());
            mv.addObject("availableProblems", availableProblems);

            // 2. Assigned Problems & Solved Problems
            List<ProblemFeedDTO> assignedToUser = problemService.getProblemsAssignedToUser(loggedInUser.getUserId());

            List<ProblemFeedDTO> assignedProblems = assignedToUser.stream()
                    .filter(p -> "IN_PROGRESS".equalsIgnoreCase(p.getStatus()))
                    .collect(Collectors.toList());

            List<ProblemFeedDTO> solvedProblems = assignedToUser.stream()
                    .filter(p -> "SOLVED".equalsIgnoreCase(p.getStatus()) || "RESOLVED".equalsIgnoreCase(p.getStatus()) || "VERIFIED".equalsIgnoreCase(p.getStatus()))
                    .collect(Collectors.toList());

            mv.addObject("assignedProblems", assignedProblems);
            mv.addObject("solvedProblems", solvedProblems);
        }

        return mv;
    }
}
