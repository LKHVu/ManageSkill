package com.example.manageskill.repository;

import com.example.manageskill.model.SkillGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillGroupRepository extends JpaRepository<SkillGroup, Long> {
    Optional<SkillGroup> findBySkillGroupName(String skillGroupName);
    @Query("SELECT s.skillId FROM Skill s WHERE s.skillGroup.skillGroupId = :skillGroupId")
    List<Long> findSkillIdsBySkillGroupId(Long skillGroupId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Skill s WHERE s.skillGroup.skillGroupId = :skillGroupId")
    void deleteBySkillGroupId(@Param("skillGroupId") Long skillGroupId);
}
