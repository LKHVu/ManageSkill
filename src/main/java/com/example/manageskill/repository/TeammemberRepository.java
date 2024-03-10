package com.example.manageskill.repository;

import com.example.manageskill.model.Teammember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeammemberRepository extends JpaRepository<Teammember, Long> {
}
