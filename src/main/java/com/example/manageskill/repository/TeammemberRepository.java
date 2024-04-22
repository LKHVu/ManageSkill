package com.example.manageskill.repository;

import com.example.manageskill.model.Team;
import com.example.manageskill.model.Teammember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeammemberRepository extends JpaRepository<Teammember, Long> {

    @Query("SELECT DISTINCT t.user.username FROM Teammember t WHERE t.team IS NOT NULL")
    List<String> findUsersWithTeam();
    List<Teammember> findByUserUsername(String username);
    @Transactional
    void deleteByTeam(Team team);
    @Transactional
    @Modifying
    @Query("DELETE FROM Teammember tm WHERE tm.user.username = :username")
    void deleteAllByUsername(@Param("username") String username);
}
