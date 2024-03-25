package com.example.manageskill.controller;

import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

        List<Object[]> teamsWithMemberCount = teamService.findAllTeamsWithMemberCount();
        model.addAttribute("teamsWithMemberCount", teamsWithMemberCount);
//        for (Object[] teamData : teamsWithMemberCount) {
//            String teamName = (String) teamData[0];
//            Long memberCount = (Long) teamData[1];
//            System.out.println( teamName + ", Member Count: " + memberCount);
//        }

        return "index";
    }
}

