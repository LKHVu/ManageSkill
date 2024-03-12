package com.example.manageskill.controller;

import com.example.manageskill.model.MemberSkill;
import com.example.manageskill.model.User;
import com.example.manageskill.service.MemberSkillService;
import com.example.manageskill.service.SkillService;
import com.example.manageskill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member-skills")
public class MemberSkillController {

    @Autowired
    private MemberSkillService memberSkillService;

    @Autowired
    private UserService userService; // Giả sử bạn có một UserService để truy vấn thông tin về người dùng

    @Autowired
    private SkillService skillService; // Giả sử bạn có một SkillService để truy vấn thông tin về kỹ năng

    @GetMapping
    public String showMemberSkillsPage(Model model) {
        List<MemberSkill> memberSkills = memberSkillService.getAllMemberSkills();
        model.addAttribute("memberSkills", memberSkills);
        return "member-skills"; // Trả về tên của view để hiển thị danh sách kỹ năng của thành viên
    }
    @GetMapping("/create")
    public String showCreateMemberSkillForm(Model model) {
        MemberSkill memberSkill = new MemberSkill();
        model.addAttribute("memberSkill", memberSkill);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("skills", skillService.getAllSkills());
        return "create-member-skill"; // Trả về tên của view để hiển thị form tạo mới memberSkill
    }

    @PostMapping("/create")
    public String createMemberSkill(@ModelAttribute("memberSkill") MemberSkill memberSkill) {
        // Để lưu MemberSkill, bạn cần thêm User vào trường user của MemberSkill trước.
        User user = userService.getUserByUsername(memberSkill.getUser().getUsername());
        memberSkill.setUser(user);
        memberSkillService.save(memberSkill); // Lưu memberSkill mới vào cơ sở dữ liệu
        return "redirect:/member-skills"; // Chuyển hướng người dùng đến trang hiển thị danh sách kỹ năng của thành viên sau khi tạo mới
    }
}
