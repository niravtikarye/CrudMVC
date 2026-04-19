package com.web.CivicSolve.Controller;

import com.web.CivicSolve.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.web.CivicSolve.Model.Problem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.web.CivicSolve.Model.UserDTO;
import com.web.CivicSolve.Service.JwtAuthFilter;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    // We will fix the dashboard later to fetch real problems
    @RequestMapping("/problem")
    public ModelAndView Problems() {
        System.out.print("this is problme:---------------:");
        ModelAndView mv = new ModelAndView("dashboard");
        // Temporarily commented out until we write the GET ALL logic
        // mv.addObject("problemList", problemService.getProblems());
        return mv;
    }

    @PostMapping("/saveProblem")
    @ResponseBody
    public String saveProblem(
            @ModelAttribute("problem") Problem problem,
            @RequestParam(value = "problemImages", required = false) MultipartFile[] files) {

        System.out.println("Save Problem method is calling..........");

        if (files != null && files.length > 5) {
            return "error: Maximum 5 images allowed.";
        }

        try {
            // Convert each uploaded image to a Base64 data URI and store as a string
            List<String> imageDataUris = new ArrayList<>();
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        byte[] bytes = file.getBytes();
                        String base64 = java.util.Base64.getEncoder().encodeToString(bytes);
                        String mimeType = file.getContentType() != null ? file.getContentType() : "image/jpeg";
                        String dataUri = "data:" + mimeType + ";base64," + base64;
                        imageDataUris.add(dataUri);
                    }
                }
            }

            // Save to DB (image_url column now holds the full base64 data URI)
            if (problem.getProbId() != null && problem.getProbId() > 0) {
                problemService.updateProblem(problem, imageDataUris);
                System.out.println("Problem Updated Successfully with ID: " + problem.getProbId());
            } else {
                Long probId = problemService.reportNewProblem(problem, imageDataUris);
                System.out.println("Problem Saved Successfully with ID: " + probId);
            }

            return "success";

        } catch (IOException e) {
            e.printStackTrace();
            return "error: File read failed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: DB insertion failed";
        }
    }

    @PostMapping("/api/problems/{probId}/delete")
    @ResponseBody
    public String deleteProblem(@PathVariable("probId") Long probId, HttpServletRequest request) {
        try {
            UserDTO loggedInUser = (UserDTO) request.getAttribute(JwtAuthFilter.USER_ATTR);
            if (loggedInUser == null) {
                return "error: Unauthorized access.";
            }
            
            problemService.deleteProblem(probId, loggedInUser.getUserId());
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: Failed to delete problem.";
        }
    }
}
