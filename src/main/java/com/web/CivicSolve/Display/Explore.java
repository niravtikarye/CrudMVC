package com.web.CivicSolve.Display;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.web.CivicSolve.Service.ProblemService;

@Controller
public class Explore {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/explore")
    public ModelAndView DisplayExplore() {
        ModelAndView mv = new ModelAndView("explore-page");
        mv.addObject("pageTitle", "Explore");
        mv.addObject("problemList", problemService.getProblems());
        mv.addObject("contentPage", "/jsp/dashboard.jsp");
        return mv;
    }
}
