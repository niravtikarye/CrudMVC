package com.web.CivicSolve.Display;

import com.web.CivicSolve.Model.ProblemFeedDTO;
import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateProblem {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/createProblem")
    public ModelAndView showCreateProblemPage(@RequestParam(value = "editProbId", required = false) Long editProbId) {
        ModelAndView mv = new ModelAndView("createProblem");
        if (editProbId != null) {
            ProblemFeedDTO problem = problemService.getProblemById(editProbId);
            mv.addObject("editProblem", problem);
        }
        return mv;
    }
}
