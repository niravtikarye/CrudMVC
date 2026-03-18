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

        List<ProblemFeedDTO> problems;

        // Render different lists based on role
        if ("citizen".equals(loggedInUser.getRole())) {
            problems = problemService.getProblemsByUserId(loggedInUser.getUserId());
            mv.addObject("feedType", "My Reported Issues");
        } else {
            // Solver
            problems = problemService.getProblemsAssignedToUser(loggedInUser.getUserId());
            mv.addObject("feedType", "My Assigned Tasks");
        }

        mv.addObject("problems", problems);
        return mv;
    }
}
