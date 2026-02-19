package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.Problem;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Solve")
public class ProblemWebController {

    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("civic_dashboard");
        // Call the in-memory API method to get the list; using the static list
        List<Problem> problems = com.web.CivicSolve.Controller.ProblemApiController.class
            .cast(com.web.CivicSolve.Controller.ProblemApiController.class)
            .getEnclosingClass() == null ? null : null;

        // Simpler: fetch by instantiating the API controller (it uses static list)
        ProblemApiController api = new ProblemApiController();
        mv.addObject("problems", api.list());
        return mv;
    }
}
