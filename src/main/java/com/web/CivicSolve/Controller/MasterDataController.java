package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Model.Area;
import com.web.CivicSolve.Model.Category;
import com.web.CivicSolve.Model.Organization;
import com.web.CivicSolve.Model.SubCategory;
import com.web.CivicSolve.Service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/master")
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;

    @GetMapping("/areas")
    public ResponseEntity<List<Area>> getAllAreas() {
        return ResponseEntity.ok(masterDataService.getAllAreas());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(masterDataService.getAllCategories());
    }

    @GetMapping("/subcategories/{categoryId}")
    public ResponseEntity<List<SubCategory>> getSubCategories(@PathVariable Long categoryId) {
        return ResponseEntity.ok(masterDataService.getSubCategoriesByCategoryId(categoryId));
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        return ResponseEntity.ok(masterDataService.getAllOrganizations());
    }
}
