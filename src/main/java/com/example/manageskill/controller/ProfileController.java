package com.example.manageskill.controller;

import com.example.manageskill.model.Teammember;
import com.example.manageskill.model.User;
import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamService teamService;
    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Lấy username của người dùng hiện tại
        User user = userRepository.findById(username).orElse(new User());
        model.addAttribute("user", user);
        List<Teammember> userTeams = teamService.getTeamsByUsername(username);
        model.addAttribute("userTeams", userTeams);
        return "profile";
    }
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {
        if (updatedUser.getUsername() != null) {
            // Retrieve the existing user from the database
            User existingUser = userRepository.findById(updatedUser.getUsername()).orElse(null);
            if (existingUser != null) {
                // Update the email and phone
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPhone(updatedUser.getPhone());
                // Save the updated user
                userRepository.save(existingUser);
            }
        }
        // Redirect back to the profile page
        return "redirect:/profile";
    }
}
