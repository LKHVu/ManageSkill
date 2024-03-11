package com.example.manageskill.repository;

import com.example.manageskill.model.Teammember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeammemberRepository extends JpaRepository<Teammember, Long> {

    @Query("SELECT DISTINCT t.user.username FROM Teammember t WHERE t.team IS NOT NULL")
    List<String> findUsersWithTeam();
}
