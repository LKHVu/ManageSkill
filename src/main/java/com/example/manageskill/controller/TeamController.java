package com.example.manageskill.controller;

import com.example.manageskill.model.Team;
import com.example.manageskill.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/teams/create")
    public String showCreateTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return "create-team";
    }
    @PostMapping("/teams")
    public String createTeam(@ModelAttribute Team team) {
        teamService.createTeam(team);
        return "redirect:/teams";
    }
    @GetMapping("/teams")
    public String showTeams(Model model) {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "teams"; // Trả về trang hiển thị danh sách team
    }

    @GetMapping("/teams/view/edit/{id}")
    public String showEditTeamForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        return "edit-team"; // Trả về trang chỉnh sửa team
    }

    @PostMapping("/teams/update")
    public String updateTeam(@RequestParam("id") Long id, @RequestParam("teamName") String teamName) {
        teamService.updateTeam(id, teamName);
        return "redirect:/teams"; // Chuyển hướng người dùng đến trang danh sách teams sau khi chỉnh sửa thành công
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return "redirect:/teams"; // Chuyển hướng người dùng đến trang danh sách teams sau khi xóa thành công
    }
}
