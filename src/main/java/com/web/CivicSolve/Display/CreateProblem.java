package com.web.CivicSolve.Display;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateProblem {

    @GetMapping("/createProblem")
    public ModelAndView showCreateProblemPage() {
        ModelAndView mv = new ModelAndView("createProblem");
        return mv;
    }
}
