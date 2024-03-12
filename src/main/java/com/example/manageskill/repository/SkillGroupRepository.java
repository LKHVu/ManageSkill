package com.example.manageskill.repository;

import com.example.manageskill.model.SkillGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillGroupRepository extends JpaRepository<SkillGroup, Long> {
}
