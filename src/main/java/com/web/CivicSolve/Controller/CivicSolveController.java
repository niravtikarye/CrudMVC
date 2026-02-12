/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.CivicSolve.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nirav
 */

@Controller
@RequestMapping("/Solve")
public class CivicSolveController {
 
     @RequestMapping("/Registration")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("Registraion_Page");
                
        return mv;
    }
}
