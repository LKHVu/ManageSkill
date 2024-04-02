package com.example.manageskill.service;

import com.example.manageskill.model.Team;
import com.example.manageskill.model.Teammember;
import com.example.manageskill.repository.TeamRepository;
import com.example.manageskill.repository.TeammemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeammemberRepository teamMemberRepository;
    public List<Teammember> getTeamsByUsername(String username) {
        return teamMemberRepository.findByUserUsername(username);
    }
    public void createTeam(Team team) {
        teamRepository.save(team);
    }
    public List<Team> getAllTeams() {
        return (List<Team>) teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.orElse(null);
    }
    public void updateTeam(Long id, String teamName) {
        Team team = getTeamById(id);
        if (team != null) {
            team.setTeamName(teamName);
            teamRepository.save(team);
        }
    }
    @Autowired
    private TeammemberRepository teammemberRepository;
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + teamId));
        teammemberRepository.deleteByTeam(team); // Xóa tất cả các Teammember của Team
        teamRepository.delete(team); // Xóa Team
    }

    public long getTeamCount() {
        return teamRepository.count();
    }
    public List<Object[]> findAllTeamsWithMemberCount() {
        return teamRepository.findAllTeamsWithMemberCount();
    }
}
