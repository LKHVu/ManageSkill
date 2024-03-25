package com.example.manageskill.repository;

import com.example.manageskill.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    long count();
}
