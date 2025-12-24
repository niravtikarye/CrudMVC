/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.crudmvc.controller;

import com.web.crudmvc.Database.Formbean.UserFormbean;
import com.web.crudmvc.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    // ============ SHOW FORM ============
    @RequestMapping("/selectProcess")
    public ModelAndView showForm(@RequestParam("process") String process,
        @RequestParam(value = "userId", required = false) Integer userId) {
        ModelAndView mv = new ModelAndView("home");
        System.out.println("Hello");
        mv.addObject("process", process);
        if (process.equals("view")) {
            mv.addObject("data", service.getAllUsers());
        } else if (process.equals("update")) {           
            mv.addObject("user",service.findById(userId));
        }
        return mv;
    }

    // ============ INSERT ============
    @RequestMapping("/save")
    public ModelAndView saveUser(@ModelAttribute("user") UserFormbean form) {
        ModelAndView mv = new ModelAndView("home");
        service.saveUser(form);

        return mv;
    }

    // ============ VIEW ============
    @RequestMapping("/list")
    public ModelAndView viewUsers() {

        ModelAndView mv = new ModelAndView("home");
        List<Map<String, Object>> users = service.getAllUsers();
        mv.addObject("users", users);

        return mv;
    }

    // ============ UPDATE ============
    @RequestMapping("/update")
    public ModelAndView updateUser(@ModelAttribute("user") UserFormbean form) {
        ModelAndView mv = new ModelAndView("home");
        service.updateUser(form);

        return mv;
    }

    // ============ DELETE ============
    @RequestMapping("/delete")
    public ModelAndView deleteUser(@ModelAttribute("user") UserFormbean form) {
        ModelAndView mv = new ModelAndView("home");
        service.deleteUser(form);
        return mv;
    }
}
