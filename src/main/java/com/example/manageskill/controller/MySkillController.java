package com.example.manageskill.controller;

import com.example.manageskill.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MySkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("/my-skill")
    public String getMySkills(Model model, Principal principal) {
        String username = principal.getName();
        List<Object[]> mySkills = skillService.getMySkills(username);
        model.addAttribute("mySkills", mySkills);
        return "my-skill";
    }
}
