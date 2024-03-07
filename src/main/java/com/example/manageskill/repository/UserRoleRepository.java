package com.example.manageskill.repository;

import com.example.manageskill.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select userRole.roleId.roleName from UserRole userRole where userRole.username.username = :username")
    List<String> findAllRoleByUser(String username);
}
