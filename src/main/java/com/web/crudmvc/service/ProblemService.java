package com.web.crudmvc.service;

import com.web.crudmvc.Database.Formbean.ProblemFormbean;
import com.web.crudmvc.repo.ProblemRepo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepo repo;

    public List<Map<String, Object>> listAll() {
        return repo.listAll();
    }

    public Map<String, Object> findById(int id) {
        return repo.findById(id);
    }

    public int create(ProblemFormbean form) {
        return repo.insertProblem(form);
    }

    public int update(ProblemFormbean form) {
        return repo.updateProblem(form);
    }

    public int delete(int id) {
        return repo.deleteProblem(id);
    }

    public int assign(int id, int solverId) {
        return repo.assignProblem(id, solverId);
    }
}
