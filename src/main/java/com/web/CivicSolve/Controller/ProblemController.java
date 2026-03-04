package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

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

    @PostMapping("/saveProblem")
    @ResponseBody
    public String saveProblem() {
        System.out.print("Save Problem method is calling..........");
        // Logic to actually save data to DB goes here...
        return "success";
    }
}
