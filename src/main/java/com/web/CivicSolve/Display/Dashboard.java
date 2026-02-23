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
        mv.addObject("problemList", problemService.getProblems());
        mv.addObject("userList", userService.getUser());
        return mv;
    }
}
