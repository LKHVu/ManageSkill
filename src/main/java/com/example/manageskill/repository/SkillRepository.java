package com.example.manageskill.repository;

import com.example.manageskill.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s JOIN FETCH s.skillGroup") // Join Fetch để lấy thông tin SkillGroup
    List<Skill> findAllWithSkillGroupName();

    @Query("SELECT s.skillName, sg.skillGroupName, ms.proficiencyLevel " +
            "FROM MemberSkill ms " +
            "JOIN ms.user u " +
            "JOIN ms.skill s " +
            "JOIN s.skillGroup sg " +
            "WHERE u.username = :username")
    List<Object[]> findMySkillsByUsername(@Param("username") String username);
}