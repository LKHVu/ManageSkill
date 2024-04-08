package com.example.manageskill.service;

import com.example.manageskill.model.Teammember;
import com.example.manageskill.repository.TeammemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeammemberService {

        @Autowired
        private TeammemberRepository teammemberRepository;

        public List<Teammember> getAllTeammembers() {
            return teammemberRepository.findAll();
        }

    public Optional<Teammember> getTeammemberById(Long id) {
        return teammemberRepository.findById(id);
    }

        public void createTeammember(Teammember teammember) {
            teammemberRepository.save(teammember);
        }
    public void save(Teammember teammember) {
        teammemberRepository.save(teammember);
    }
    public void deleteTeamMember(Long id) {
        teammemberRepository.deleteById(id);
    }
    }
