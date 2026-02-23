package com.web.CivicSolve.Service;

import org.springframework.stereotype.Service;
import com.web.CivicSolve.Model.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<User> getUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Rooteek", "root_user",
                "https://i.pinimg.com/736x/ca/c6/d8/cac6d852725aa673ffe24f9f955e6ba1.jpg"));

        userList.add(new User("nirav", "nirav_nt",
                "https://i.pinimg.com/736x/79/06/3c/79063cde98330c094611628cf7a16e4f.jpg"));

        userList.add(new User("lokesh", "lockx",
                "https://i.pinimg.com/736x/e4/7b/b1/e47bb146eef3dae0d6903b0fbec9f6d9.jpg"));

        userList.add(new User("swap", "sweet",
                "https://i.pinimg.com/1200x/65/41/68/654168a2f9b30b8104fe5c6b981aa809.jpg"));

        userList.add(new User("raj", "rock",
                "https://i.pinimg.com/736x/41/9b/1b/419b1b6074fb8ed5a405659abebc10c5.jpg"));

        userList.add(new User("om", "om",
                "https://i.pinimg.com/736x/79/1d/4b/791d4b4d7b9f13ad0ea1d3de445d398c.jpg"));

        userList.add(new User("Sagar", "sag",
                "https://i.pinimg.com/736x/19/8f/74/198f74a088d61f54a7c08329d1d4ba9a.jpg"));

        userList.add(new User("Prem", "prem",
                "https://i.pinimg.com/736x/5f/5b/b2/5f5bb23f7174f654a2ab4445a0bc856c.jpg"));

        userList.add(new User("ranvir", "ranvir",
                "https://i.pinimg.com/736x/5d/70/b1/5d70b156c3399e367df156614bb6b72b.jpg"));
        return userList;
    }
}
