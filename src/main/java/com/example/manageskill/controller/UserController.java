package com.example.manageskill.controller;

import com.example.manageskill.model.Role;
import com.example.manageskill.model.User;
import com.example.manageskill.model.UserRole;
import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/members")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Autowire PasswordEncoder
    @GetMapping
    public String showMembersPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "members"; // Assuming the HTML file is named members.html
    }
    @GetMapping("/add-user")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-user";
        }

        try {
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "User has been added successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to add user. Please try again.");
        }

        return "redirect:/members/add-user";
    }

    @PostMapping("/create-account")
    public String createAccount(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("role") String roleName,
                                RedirectAttributes redirectAttributes) {
        try {
            String hashedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);
            userRepository.save(user);

            UserRole userRole = new UserRole();
            Role role = new Role();
            if (roleName.equals("ROLE_ADMIN")) {
                role.setRoleId(1L); // Thiết lập roleId cho ROLE_ADMIN
            } else if (roleName.equals("ROLE_MEMBERS")) {
                role.setRoleId(2L); // Thiết lập roleId cho ROLE_MEMBERS
            } else {
                throw new IllegalArgumentException("Invalid role!"); // Xử lý trường hợp role không hợp lệ
            }
            userRole.setRoleId(role);
            userRole.setUsername(user);
            userRoleRepository.save(userRole);

            redirectAttributes.addFlashAttribute("message", "User account has been created successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to create user account. Please try again.");
        }

        return "redirect:/members/add-user";
    }

    @GetMapping("/update-user/{username}")
    public String showUpdateForm(@PathVariable("username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update-user/{username}")
    public String updateUser(@PathVariable("username") String username, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update-user";
        }

        try {
            // Update user in the database
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "User has been updated successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user. Please try again.");
        }

        return "redirect:/members";
    }

    @GetMapping("/delete-user/{username}")
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {
        try {
            // Delete user from the database
            userRepository.deleteById(username);
            redirectAttributes.addFlashAttribute("message", "User has been deleted successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete user. Please try again.");
        }

        return "redirect:/members";
    }
}
