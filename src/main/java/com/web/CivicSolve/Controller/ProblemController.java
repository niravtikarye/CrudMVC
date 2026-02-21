package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @RequestMapping("/problem")
    public ModelAndView Problems() {
        System.out.print("this is problme:---------------:");
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("problemList", problemService.getProblems());
        return mv;
    }
}
