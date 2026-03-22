package com.web.CivicSolve.Display;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.web.CivicSolve.Service.ProblemService;
import com.web.CivicSolve.Service.MasterDataService;

@Controller
public class Explore {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MasterDataService masterDataService;

    @GetMapping("/explore")
    public ModelAndView DisplayExplore(
            @RequestParam(value="areaId", required=false) Long areaId,
            @RequestParam(value="categoryId", required=false) Long categoryId,
            @RequestParam(value="status", required=false) String status) {
            
        ModelAndView mv = new ModelAndView("explore-page");
        mv.addObject("pageTitle", "Explore");
        
        // Fetch real data from ProblemService based on filters
        mv.addObject("problemList", problemService.getFilteredFeedProblems(areaId, categoryId, status));
        
        // Data for dropdowns
        mv.addObject("areas", masterDataService.getAllAreas());
        mv.addObject("categories", masterDataService.getAllCategories());
        
        // Maintain UI state
        mv.addObject("selectedArea", areaId);
        mv.addObject("selectedCategory", categoryId);
        mv.addObject("selectedStatus", status);

        mv.addObject("contentPage", "/jsp/explore-page.jsp");
        return mv;
    }
}
