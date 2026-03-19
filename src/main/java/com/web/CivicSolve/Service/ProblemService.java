package com.web.CivicSolve.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.CivicSolve.Model.Problem;
import com.web.CivicSolve.Model.ProblemFeedDTO;
import com.web.CivicSolve.Repo.ProblemRepo;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepo problemRepo;

    public Long reportNewProblem(Problem problem, List<String> imageUrls) {
        // 1. Save the core problem to the problems table
        Long generatedProbId = problemRepo.createProblem(problem);

        // 2. If there are images, save them to the problem_images table
        if (imageUrls != null && !imageUrls.isEmpty()) {
            problemRepo.saveProblemImages(generatedProbId, imageUrls, "before");
        }

        return generatedProbId;
    }

    public List<ProblemFeedDTO> getAllFeedProblems() {
        return problemRepo.getAllFeedProblems();
    }

    public List<ProblemFeedDTO> getProblemsByUserId(Long userId) {
        return problemRepo.getProblemsByUserId(userId);
    }

    public List<ProblemFeedDTO> getProblemsAssignedToUser(Long solverId) {
        return problemRepo.getProblemsAssignedToUser(solverId);
    }

    /**
     * Toggles a hype for a user. Returns true if added, false if they already hyped it.
     */
    public boolean toggleHype(Long probId, Long userId) {
        if (problemRepo.checkUserHyped(probId, userId)) {
            return false; // Already hyped
        }
        problemRepo.addHype(probId, userId);
        return true;   // Successfully added
    }

    /**
     * Assigns a problem solver to the task.
     */
    public void assignSolver(Long probId, Long solverId, Long assignedBy) {
        problemRepo.assignProblem(probId, solverId, assignedBy);
    }

    /**
     * Marks a problem as solved and links the "After" image proof.
     */
    public void markProblemSolved(Long probId, Long solverId, String imageUrl) {
        problemRepo.markProblemSolved(probId, solverId);
        
        if (imageUrl != null && !imageUrl.isEmpty()) {
            problemRepo.saveProblemImages(probId, java.util.Collections.singletonList(imageUrl), "after");
        }
    }

    /**
     * Verifies or rejects the solver's work.
     * @param status Must be 'VERIFIED' or 'RE_OPENED'
     */
    public void verifyProblem(Long probId, Long authorId, String status) {
        problemRepo.verifyProblem(probId, authorId, status);
    }
}
