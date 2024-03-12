package com.example.manageskill.controller;

import com.example.manageskill.model.Skill;
import com.example.manageskill.model.SkillGroup;
import com.example.manageskill.service.SkillGroupService;
import com.example.manageskill.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillGroupService skillGroupService;

    @GetMapping
    public String showSkillsPage(Model model) {
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "skills";
    }

    @GetMapping("/create")
    public String showCreateSkillForm(Model model) {
        model.addAttribute("skill", new Skill());
        model.addAttribute("skillGroups", skillGroupService.getAllSkillGroups());
        return "create-skill";
    }

    @PostMapping("/create")
    public String createSkill(@ModelAttribute Skill skill) {
        skillService.createSkill(skill);
        return "redirect:/skills";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateSkillForm(@PathVariable Long id, Model model) {
        Optional<Skill> skillOptional = skillService.getSkillById(id);
        Skill skill = skillOptional.orElse(null);
        List<SkillGroup> skillGroups = skillGroupService.getAllSkillGroups();
        model.addAttribute("skill", skill);
        model.addAttribute("skillGroups", skillGroups);
        return "edit-skill";
    }


    @PostMapping("/update")
    public String updateSkill(@ModelAttribute Skill skill) {
        skillService.updateSkill(skill);
        return "redirect:/skills";
    }

    @GetMapping("/delete/{id}")
    public String deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return "redirect:/skills";
    }
}
