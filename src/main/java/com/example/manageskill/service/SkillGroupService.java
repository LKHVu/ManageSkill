package com.example.manageskill.service;

import com.example.manageskill.model.SkillGroup;
import com.example.manageskill.repository.SkillGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteSkillGroup(Long id) {

        skillGroupRepository.deleteById(id);
    }
}