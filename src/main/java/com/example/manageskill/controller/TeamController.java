package com.example.manageskill.controller;

import com.example.manageskill.model.Team;
import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.service.TeamService;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/teams")
    public String list(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "teams";
    }

    @GetMapping("/my-teams")
    public String myTeams(Model model, Authentication auth) {
        List<Team> owned = teamService.findTeamsOwnedBy(auth.getName());
        List<Team> memberTeams = teamService.findTeamsForUser(auth.getName());

        // merge distinct, preserving order (owned first)
        HashSet<Long> seen = new HashSet<Long>();
        List<Team> combined = new java.util.ArrayList<Team>();
        for (Team t : owned) {
            if (seen.add(t.getTeamId())) {
                combined.add(t);
            }
        }
        for (Team t : memberTeams) {
            if (seen.add(t.getTeamId())) {
                combined.add(t);
            }
        }

        model.addAttribute("teams", combined);
        return "teams"; // same template; admin-only actions hidden by role checks
    }

    @GetMapping("/teams/create")
    public String showCreateTeamForm(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("users", userRepository.findAll()); // for owner select
        return "create-team";
    }

    @PostMapping("/teams")
    public String createTeam(@RequestParam String teamName,
            @RequestParam(required = false, name = "ownerUsername") String ownerUsername) {
        teamService.createTeam(teamName, ownerUsername);
        return "redirect:/teams";
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

    @GetMapping("/teams/{id}")
    public String teamDetails(@PathVariable Long id, Model model, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        boolean isOwner = teamService.isOwnerOfTeam(id, auth.getName());
        boolean isMember = teamService.isMemberOfTeam(id, auth.getName());

        if (!(isAdmin || isOwner || isMember)) {
            return "deny"; // not allowed to view
        }

        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("members", teamService.membersOf(id));
        model.addAttribute("allUsers", userRepository.findAll());

        // only admins or owners can edit (name/owner/add/remove)
        model.addAttribute("canManage", isAdmin || isOwner);
        return "team-details";
    }

    @PostMapping("/teams/{id}/members/add")
    public String addMember(@PathVariable Long id, @RequestParam String username, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (!(isAdmin || teamService.isOwnerOfTeam(id, auth.getName()))) {
            return "deny";
        }
        teamService.addMember(id, username);
        return "redirect:/teams/" + id;
    }

    @PostMapping("/teams/{id}/members/remove")
    public String removeMember(@PathVariable Long id, @RequestParam String username, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (!(isAdmin || teamService.isOwnerOfTeam(id, auth.getName()))) {
            return "deny";
        }
        teamService.removeMember(id, username);
        return "redirect:/teams/" + id;
    }

    @PostMapping("/teams/update")
    public String updateTeam(@RequestParam Long id,
            @RequestParam String teamName,
            @RequestParam(required = false, name = "ownerUsername") String ownerUsername,
            Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (!(isAdmin || teamService.isOwnerOfTeam(id, auth.getName()))) {
            return "deny";
        }

        teamService.updateTeam(id, teamName, ownerUsername);
        boolean stillOwner = ownerUsername != null && !ownerUsername.isEmpty()
                && ownerUsername.equalsIgnoreCase(auth.getName());
        return (isAdmin || stillOwner) ? "redirect:/teams/" + id : "redirect:/my-teams";
    }
}
