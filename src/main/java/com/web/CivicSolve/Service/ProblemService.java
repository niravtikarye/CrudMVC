package com.web.CivicSolve.Service;

import org.springframework.stereotype.Service;
import com.web.CivicSolve.Model.Problem;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProblemService {

    public List<Problem> getProblems() {
        List<Problem> list = new ArrayList<>();
        list.add(new Problem(
                "ROAD ISSUE",
                "Pothole on VIP Road",
                "Large pothole near VIP Circle causing traffic and accidents.",
                new String[]{
                    "https://i.pinimg.com/736x/fc/41/ae/fc41ae82a5b1ec3531a7febf20ee04bc.jpg",
                    "https://i.pinimg.com/736x/fc/41/ae/fc41ae82a5b1ec3531a7febf20ee04bc.jpg",
                    "https://i.pinimg.com/1200x/17/86/d1/1786d17b4765f4c0ecaea37e935076d3.jpg",
                }
        ));

        list.add(new Problem(
                "WATER SUPPLY",
                "Water leakage in Utran",
                "Water pipeline leaking continuously since last 3 days.",
                new String[]{"https://i.pinimg.com/736x/87/0e/33/870e332750efe9c3a067f4e574d9399a.jpg"}
        ));

        list.add(new Problem(
                "STREET LIGHT",
                "Street light not working",
                "Street light not functioning near Kapodra Ward.",
                new String[]{"https://i.pinimg.com/736x/91/b4/df/91b4df7255162491033ebae7e6a5616b.jpg"}
        ));

        return list;
    }
}
