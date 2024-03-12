package com.example.manageskill.repository;

import com.example.manageskill.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s JOIN FETCH s.skillGroup") // Join Fetch để lấy thông tin SkillGroup
    List<Skill> findAllWithSkillGroupName();

}