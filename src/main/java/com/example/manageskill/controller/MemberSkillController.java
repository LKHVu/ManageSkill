package com.example.manageskill.controller;

import com.example.manageskill.model.MemberSkill;
import com.example.manageskill.model.Skill;
import com.example.manageskill.model.User;
import com.example.manageskill.service.MemberSkillService;
import com.example.manageskill.service.SkillService;
import com.example.manageskill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member-skills")
public class MemberSkillController {

    @Autowired
    private MemberSkillService memberSkillService;

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @GetMapping
    public String showMemberSkillsPage(Model model) {
        List<MemberSkill> memberSkills = memberSkillService.getAllMemberSkills();
        model.addAttribute("memberSkills", memberSkills);
        return "member-skills";
    }
    @GetMapping("/create")
    public String showCreateMemberSkillForm(Model model) {

        List<User> users = userService.getAllUsers();
        MemberSkill memberSkill = new MemberSkill();

        // Thêm danh sách người dùng vào model để hiển thị trong form
        model.addAttribute("memberSkill", memberSkill);
        model.addAttribute("users", users);
        model.addAttribute("skills", skillService.getAllSkills());

        return "create-member-skill";
    }

    @PostMapping("/create")
    public String createMemberSkill(@ModelAttribute("memberSkill") MemberSkill memberSkill) {
        memberSkillService.save(memberSkill);
        return "redirect:/member-skills";
    }
    @GetMapping("/delete/{id}")
    public String deleteMemberSkill(@PathVariable Long id) {
        memberSkillService.deleteMemberSkill(id);
        return "redirect:/member-skills";
    }
    @GetMapping("/update/{id}")
    public String showUpdateMemberSkillForm(@PathVariable("id") Long id, Model model) {
        MemberSkill memberSkill = memberSkillService.getMemberSkillById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member skill Id:" + id));

        List<User> users = userService.getUsersNotInMemberSkill();
        List<Skill> skills = skillService.getAllSkills();

        model.addAttribute("memberSkill", memberSkill);
        model.addAttribute("users", users);
        model.addAttribute("skills", skills);

        return "edit-member-skill";
    }

    @PostMapping("/update")
    public String updateMemberSkill(@ModelAttribute("memberSkill") MemberSkill memberSkill) {
        memberSkillService.save(memberSkill);
        return "redirect:/member-skills";
    }


}
