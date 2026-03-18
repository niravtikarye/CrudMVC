package com.web.CivicSolve.Display;

import org.springframework.stereotype.Controller;
import com.web.CivicSolve.Service.ProblemService;
import com.web.CivicSolve.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Dashboard {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView DisplayDashboard() {
        ModelAndView mv = new ModelAndView("dashboard");
        // Fetch real data from ProblemService
        mv.addObject("problemList", problemService.getAllFeedProblems());
        mv.addObject("contentPage", "/jsp/dashboard.jsp");
        mv.addObject("userList", userService.getUser());
        return mv;
    }

    @GetMapping("/solver-list")
    public ModelAndView GetSolverList() {
        ModelAndView mv = new ModelAndView("get-solver-list");
        mv.addObject("userList", userService.getUser());
        mv.addObject("contentPage", "/jsp/get-solver-list.jsp");
        return mv;
    }
}
