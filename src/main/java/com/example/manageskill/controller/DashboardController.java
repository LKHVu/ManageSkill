package com.example.manageskill.controller;

import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;
    @GetMapping("/")
    public String chart(Model model) {
        // Truy vấn số lượng người dùng từ cơ sở dữ liệu
        long userCount = userRepository.count();

        // Trả về số lượng người dùng cho view
        model.addAttribute("userCount", userCount);
        long teamCount = teamService.getTeamCount();
        model.addAttribute("teamCount", teamCount);
        // Trả về trang HTML
        return "index";
    }
}

