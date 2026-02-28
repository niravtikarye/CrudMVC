package com.web.CivicSolve.Display;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Explore {
    @GetMapping("/explore")
    public ModelAndView DisplayExplore(){
        ModelAndView mv = new ModelAndView("explore-page");
        return mv;
    }
}
