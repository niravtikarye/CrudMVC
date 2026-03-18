package com.web.CivicSolve.Service;

import com.web.CivicSolve.Model.Area;
import com.web.CivicSolve.Model.Category;
import com.web.CivicSolve.Model.Organization;
import com.web.CivicSolve.Model.SubCategory;
import com.web.CivicSolve.Repo.MasterDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataService {

    @Autowired
    private MasterDataRepo masterDataRepo;

    public List<Area> getAllAreas() {
        return masterDataRepo.getAllAreas();
    }

    public List<Category> getAllCategories() {
        return masterDataRepo.getAllCategories();
    }

    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId) {
        return masterDataRepo.getSubCategoriesByCategoryId(categoryId);
    }

    public List<Organization> getAllOrganizations() {
        return masterDataRepo.getAllOrganizations();
    }
}
