package com.example.manageskill.controller;

import com.example.manageskill.model.Team;
import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;   // <-- add
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired private UserRepository userRepository;
    @Autowired private TeamService teamService;

    @GetMapping("/")
    public String chart(Model model, Authentication auth) { // <-- accept auth
        // counters
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("teamCount", teamService.getTeamCount());
        model.addAttribute("teamsWithMemberCount", teamService.findAllTeamsWithMemberCount());

        // expose ownership info for ROLE_MEMBERS who are team owners
        if (auth != null) {
            String username = auth.getName();
            List<Team> owned = teamService.findTeamsOwnedBy(username);  // list of teams they own
            model.addAttribute("isOwner", !owned.isEmpty());
            model.addAttribute("ownedTeams", owned);
            model.addAttribute("ownedTeamId", owned.size() == 1 ? owned.get(0).getTeamId() : null);
        } else {
            model.addAttribute("isOwner", false);
            model.addAttribute("ownedTeams", List.of());
            model.addAttribute("ownedTeamId", null);
        }
        return "index";
    }
}
