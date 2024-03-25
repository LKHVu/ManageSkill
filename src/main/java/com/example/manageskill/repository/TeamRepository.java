package com.example.manageskill.repository;

import com.example.manageskill.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    long count();
    @Query("SELECT t.teamName, COUNT(tm.memberId) AS member_count\n" +
            "FROM Team t\n" +
            "JOIN Teammember tm ON t.teamId = tm.team.teamId\n" +
            "GROUP BY t.teamName")
    List<Object[]> findAllTeamsWithMemberCount();
}
