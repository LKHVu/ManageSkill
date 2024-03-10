package com.example.manageskill.controller;

import com.example.manageskill.model.Teammember;
import com.example.manageskill.service.TeammemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TeammemberController {
    @Autowired
    private TeammemberService teammemberService;

    @GetMapping("/teammembers")
    public String showTeammembersPage(Model model) {
        return "teammembers";
    }

    @GetMapping("/teammembers/view")
    public String showViewTeammembersPage(Model model) {
        List<Teammember> teammembers = teammemberService.getAllTeammembers();
        model.addAttribute("teammembers", teammembers);
        return "view-teammembers";
    }

    @GetMapping("/teammembers/create")
    public String showCreateTeammemberForm(Model model) {
        // Chuyển hướng đến trang tạo mới teammember
        return "create-teammember";
    }
}
