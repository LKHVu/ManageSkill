package com.example.manageskill.controller;

import com.example.manageskill.model.SkillGroup;
import com.example.manageskill.service.SkillGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skill-groups")
public class SkillGroupController {

    @Autowired
    private SkillGroupService skillGroupService;

    @GetMapping
    public String showSkillGroupsPage(Model model) {
        List<SkillGroup> skillGroups = skillGroupService.getAllSkillGroups();
        model.addAttribute("skillGroups", skillGroups);
        return "skill-groups";
    }

    @GetMapping("/create")
    public String showCreateSkillGroupForm(Model model) {
        model.addAttribute("skillGroup", new SkillGroup());
        return "create-skill-group";
    }

    @PostMapping("/create")
    public String createSkillGroup(@ModelAttribute SkillGroup skillGroup) {
        skillGroupService.createSkillGroup(skillGroup);
        return "redirect:/skill-groups";
    }

    @GetMapping("/edit/{id}")
    public String showEditSkillGroupForm(@PathVariable Long id, Model model) {
        Optional<SkillGroup> skillGroup = skillGroupService.getSkillGroupById(id);
        model.addAttribute("skillGroup", skillGroup.orElse(null));
        return "edit-skill-group";
    }

    @PostMapping("/update")
    public String updateSkillGroup(@ModelAttribute SkillGroup skillGroup) {
        skillGroupService.updateSkillGroup(skillGroup);
        return "redirect:/skill-groups";
    }

    @GetMapping("/delete/{id}")
    public String deleteSkillGroup(@PathVariable Long id) {
        skillGroupService.deleteSkillGroup(id);
        return "redirect:/skill-groups";
    }
}
