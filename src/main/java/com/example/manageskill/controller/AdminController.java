package com.example.manageskill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Return the name of the admin page (admin.html)
    }
    @GetMapping("/teams")
    public String adminTeam() {
        return "teams"; // Return the name of the admin page (teams.html)
    }
    @GetMapping("/members")
    public String adminMember() {
        return "members"; // Return the name of the admin page (members.html)
    }
}
