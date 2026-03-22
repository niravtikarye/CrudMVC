package com.web.CivicSolve.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.CivicSolve.Model.Problem;
import com.web.CivicSolve.Model.ProblemFeedDTO;
import com.web.CivicSolve.Repo.ProblemRepo;
import org.springframework.scheduling.annotation.Scheduled;
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

    public List<ProblemFeedDTO> getFilteredFeedProblems(Long areaId, Long categoryId, String status) {
        return problemRepo.getFilteredFeedProblems(areaId, categoryId, status);
    }

    public ProblemFeedDTO getProblemById(Long probId) {
        return problemRepo.getProblemById(probId);
    }

    public void updateProblem(Problem problem, List<String> imageUrls) {
        problemRepo.updateProblem(problem);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            // To prevent duplicates, purge previous citizen uploads before inserting new batch
            problemRepo.deleteCitizenImages(problem.getProbId());
            problemRepo.saveProblemImages(problem.getProbId(), imageUrls, "before");
        }
    }

    public void deleteProblem(Long probId, Long userId) {
        problemRepo.deleteProblem(probId, userId);
    }

    public List<ProblemFeedDTO> getProblemsByUserId(Long userId) {
        return problemRepo.getProblemsByUserId(userId);
    }

    public List<ProblemFeedDTO> getProblemsAssignedToUser(Long solverId) {
        return problemRepo.getProblemsAssignedToUser(solverId);
    }

    /**
     * Toggles a hype for a user. Returns "added" if it was added, "removed" if it had 
     * already been hyped and is now removed.
     */
    public String toggleHype(Long probId, Long userId) {
        if (problemRepo.checkUserHyped(probId, userId)) {
            problemRepo.removeHype(probId, userId);
            return "removed"; // Was hyped previously, so it gets unhyped
        }
        problemRepo.addHype(probId, userId);
        return "added"; // Successfully added
    }

    /**
     * Assigns a problem solver to the task.
     */
    public void assignSolver(Long probId, Long solverId) {
        problemRepo.assignProblem(probId, solverId);
    }

    /**
     * Marks a problem as solved and links the "After" image proof.
     */
    public void markProblemSolved(Long probId, Long solverId, String imageUrl, String solverDesc) {
        problemRepo.markProblemSolved(probId);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            problemRepo.saveProblemImages(probId, java.util.Collections.singletonList(imageUrl), "after");
        }
        
        if (solverDesc != null && !solverDesc.trim().isEmpty()) {
            problemRepo.updateSolverDescription(probId, solverDesc);
        }
    }

    /**
     * Verifies or rejects the solver's work.
     * 
     * @param status Must be 'VERIFIED' or 'RE_OPENED'
     */
    public void verifyProblem(Long probId, String status) {
        boolean status_bool = (status.equalsIgnoreCase("VERIFIED") ? true : false);        
        problemRepo.verifyProblem(probId,  status_bool);        
    }

    /**
     * Unassigns a problem from its current solver and resets it to PENDING.
     */
    public void unassignProblem(Long probId) {
        problemRepo.unassignProblem(probId);
    }

    /**
     * Runs every hour to auto-release problems assigned but not solved within 48
     * hours.
     */
    @Scheduled(cron = "0 0 * * * *") // Runs at the top of every hour
    public void autoReleaseOverdueProblems() {
        int releasedCount = problemRepo.autoUnassignOverdueProblems();
        if (releasedCount > 0) {
            System.out.println("[Scheduled] Auto-released " + releasedCount + " overdue problems based on 48h limit.");
        }
    }
}
