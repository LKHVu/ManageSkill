package com.example.manageskill.service;

import com.example.manageskill.model.MemberSkill;
import com.example.manageskill.repository.MemberSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberSkillService {

    @Autowired
    private MemberSkillRepository memberSkillRepository;

    public MemberSkill save(MemberSkill memberSkill) {
        return memberSkillRepository.save(memberSkill);
    }

    public List<MemberSkill> getAllMemberSkills() {
        return memberSkillRepository.findAll();
    }

    public Optional<MemberSkill> getMemberSkillById(Long id) {
        return memberSkillRepository.findById(id);
    }

    public void deleteMemberSkill(Long id) {
        memberSkillRepository.deleteById(id);
    }
}

