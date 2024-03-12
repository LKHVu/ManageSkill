package com.example.manageskill.controller;

import com.example.manageskill.model.MemberSkill;
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
    private UserService userService; // Giả sử bạn có một UserService để truy vấn thông tin về người dùng

    @Autowired
    private SkillService skillService; // Giả sử bạn có một SkillService để truy vấn thông tin về kỹ năng

    @GetMapping
    public String showMemberSkillsPage(Model model) {
        List<MemberSkill> memberSkills = memberSkillService.getAllMemberSkills();
        model.addAttribute("memberSkills", memberSkills);
        return "member-skills"; // Trả về tên của view để hiển thị danh sách kỹ năng của thành viên
    }
    @GetMapping("/create") // Đường dẫn cụ thể cho trang tạo mới
    public String showCreateMemberSkillForm(Model model) {
        // Lấy danh sách người dùng chưa có trong MemberSkill
        List<User> usersNotInMemberSkill = userService.getUsersNotInMemberSkill();

        // Tạo một đối tượng MemberSkill mới
        MemberSkill memberSkill = new MemberSkill();

        // Thêm danh sách người dùng vào model để hiển thị trong form
        model.addAttribute("memberSkill", memberSkill);
        model.addAttribute("users", usersNotInMemberSkill);
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
}
