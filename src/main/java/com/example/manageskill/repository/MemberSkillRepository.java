package com.example.manageskill.repository;

import com.example.manageskill.model.MemberSkill;
import com.example.manageskill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {
    // Truy vấn danh sách người dùng đã có trong MemberSkill
    @Query("SELECT DISTINCT m.user FROM MemberSkill m")
    List<User> findAllUsers();
    @Query("SELECT m FROM MemberSkill m WHERE m.skill.skillId = :skillId")
    List<MemberSkill> findAllBySkillId(Long skillId);
}
