package com.example.manageskill.service;

import com.example.manageskill.model.Team;
import com.example.manageskill.model.Teammember;
import com.example.manageskill.model.User;
import com.example.manageskill.repository.TeamRepository;
import com.example.manageskill.repository.TeammemberRepository;
import com.example.manageskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeammemberRepository teamMemberRepository;
    @Autowired
    private UserRepository userRepository; // add this

    public List<Teammember> getTeamsByUsername(String username) {
        return teamMemberRepository.findByUserUsername(username);
    }

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    public Team createTeam(String teamName, String ownerUsername) {
        Team t = new Team();
        t.setTeamName(teamName);
        if (ownerUsername != null && !ownerUsername.isEmpty()) {
            userRepository.findById(ownerUsername).ifPresent(t::setOwner);
        }
        t = teamRepository.save(t);

        // ensure owner is also a member
        if (t.getOwner() != null) {
            ensureMember(t, t.getOwner());
        }
        return t;
    }

    public void updateTeam(Long id, String teamName, String ownerUsername) {
        Team team = getTeamById(id);
        if (team == null) {
            return;
        }

        User oldOwner = team.getOwner();
        team.setTeamName(teamName);

        if (ownerUsername != null && !ownerUsername.isEmpty()) {
            userRepository.findById(ownerUsername).ifPresent(team::setOwner);
        } else {
            team.setOwner(null);
        }
        team = teamRepository.save(team);

        // keep membership consistent
        if (team.getOwner() != null) {
            ensureMember(team, team.getOwner());
        }
    }

    private void ensureMember(Team team, User user) {
        if (!teamMemberRepository.existsByTeamAndUser(team, user)) {
            Teammember m = new Teammember();
            m.setTeam(team);
            m.setUser(user);
            teamMemberRepository.save(m);
        }
    }

    public List<Teammember> membersOf(Long teamId) {
        Team team = getTeamById(teamId);
        return team == null ? List.of() : teamMemberRepository.findByTeam(team);
    }

    public void addMember(Long teamId, String username) {
        Team team = getTeamById(teamId);
        if (team == null) {
            return;
        }
        userRepository.findById(username).ifPresent(u -> {
            if (!teamMemberRepository.existsByTeamAndUser(team, u)) {
                Teammember m = new Teammember();
                m.setTeam(team);
                m.setUser(u);
                teamMemberRepository.save(m);
            }
        });
    }

    @Transactional
    public void removeMember(Long teamId, String username) {
        Team team = getTeamById(teamId);
        if (team == null) {
            return;
        }

        // if this user is the owner, clear the owner field first (simple rule)
        if (team.getOwner() != null && username.equals(team.getOwner().getUsername())) {
            team.setOwner(null);
            teamRepository.save(team);
        }

        userRepository.findById(username).ifPresent(u -> {
            teamMemberRepository.deleteByTeamAndUser(team, u);
        });
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

    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + teamId));
        teamMemberRepository.deleteByTeam(team); // Xóa tất cả các Teammember của Team
        teamRepository.delete(team); // Xóa Team
    }

    public long getTeamCount() {
        return teamRepository.count();
    }

    public List<Object[]> findAllTeamsWithMemberCount() {
        return teamRepository.findAllTeamsWithMemberCount();
    }

    public boolean isOwnerOfTeam(Long teamId, String username) {
        Team t = getTeamById(teamId);
        return t != null && t.getOwner() != null
                && username.equals(t.getOwner().getUsername());
    }

    public boolean isAnyTeamOwner(String username) {
        return teamRepository.existsByOwnerUsername(username);
    }

    public List<Team> findTeamsOwnedBy(String username) {
        return teamRepository.findByOwnerUsername(username);
    }

    public boolean isMemberOfTeam(Long teamId, String username) {
        return teamMemberRepository.existsByTeamTeamIdAndUserUsername(teamId, username);
    }

    public List<Team> findTeamsForUser(String username) {
        // Build from membership rows
        return teamMemberRepository.findByUserUsername(username)
                .stream()
                .map(Teammember::getTeam)
                .distinct()
                .toList();
    }

}
