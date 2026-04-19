package com.web.CivicSolve.Display;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthViews {

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }
}
