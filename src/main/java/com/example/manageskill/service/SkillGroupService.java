package com.example.manageskill.service;

import com.example.manageskill.model.SkillGroup;
import com.example.manageskill.repository.MemberSkillRepository;
import com.example.manageskill.repository.SkillGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class SkillGroupService {

    @Autowired
    private SkillGroupRepository skillGroupRepository;

    public List<SkillGroup> getAllSkillGroups() {
        return skillGroupRepository.findAll();
    }

    public Optional<SkillGroup> getSkillGroupById(Long id) {
        return skillGroupRepository.findById(id);
    }

    public SkillGroup createSkillGroup(SkillGroup skillGroup) {
        return skillGroupRepository.save(skillGroup);
    }

    public void updateSkillGroup(SkillGroup skillGroup) {

        skillGroupRepository.save(skillGroup);
    }

    //    public void deleteSkillGroup(Long id) {
//
//        skillGroupRepository.deleteById(id);
//    }


    @Autowired
    private MemberSkillRepository memberSkillRepository;


    @Transactional
    public void deleteSkillGroup(Long skillGroupId) {
        //  Lấy danh sách skillId thuộc skillGroupId
        List<Long> skillIds = skillGroupRepository.findSkillIdsBySkillGroupId(skillGroupId);
        for (Long skillId : skillIds) {
            System.out.println(skillId);
        }
        //  Xóa tất cả các MemberSkill liên quan đến các Skill
        for (Long skillId : skillIds) {
            memberSkillRepository.deleteBySkillId(skillId);
        }
        // cho nay k hieu tai sao no lai nhu the huhuuhuhuhuhu
        //nhung ma code no chay
       skillGroupRepository.deleteBySkillGroupId(skillGroupId);
        skillGroupRepository.deleteById(skillGroupId);
    }
}