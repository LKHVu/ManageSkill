package com.example.manageskill.service;

import com.example.manageskill.model.Teammember;
import com.example.manageskill.repository.TeammemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeammemberService {

        @Autowired
        private TeammemberRepository teammemberRepository;

        public List<Teammember> getAllTeammembers() {
            return teammemberRepository.findAll();
        }

        public Teammember getTeammemberById(Long id) {
            return teammemberRepository.findById(id).orElse(null);
        }

        public void createTeammember(Teammember teammember) {
            teammemberRepository.save(teammember);
        }

//        public void updateTeammember(Long id, Teammember updatedTeammember) {
//            Teammember existingTeammember = teammemberRepository.findById(id).orElse(null);
//            if (existingTeammember != null) {
//                existingTeammember.set... // Cập nhật thông tin của teammember
//                teammemberRepository.save(existingTeammember);
//            }
//        }
//
//        public void deleteTeammember(Long id) {
//            teammemberRepository.deleteById(id);
//        }
    }
