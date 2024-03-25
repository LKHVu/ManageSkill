package com.example.manageskill.controller;

import com.example.manageskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

   /* @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPasswordHash().equals(password)) {
            // Đăng nhập thành công
            retun ResponseEntity.ok("Login successful");
        } else {
            // Đăng nhập thất bại
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/

//    @RequestMapping("/")
//    public String home() {
//        return "index";
//    }


    @RequestMapping("/login")
    public String login(@RequestParam("error") Optional<String> error, Model model) {
        if (error.isPresent()) {
            model.addAttribute("error", error.get());
        }
        return "login";
    }

    @GetMapping("/deny")
    public String denied() {
        return "denied";
    }
}
