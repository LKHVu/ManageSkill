package com.example.manageskill.repository;

import com.example.manageskill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();

    User findByUsername(String username);

    // NEW: allow lookup by email
    User findByEmail(String email);

    // (Optional, but handy) single call for username OR email
    @Query("SELECT u FROM User u WHERE u.username = :input OR u.email = :input")
    User findByUsernameOrEmail(@Param("input") String input);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
}
