package com.example.manageskill.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "password"; // this is what you want to test
        String hashInDb = "$2a$10$Dow1FKPeM5NfS0pMEOl4yO.6Ff1iHsO8z5o3rRo4aJY1oOa.9t4bq";

        boolean matches = encoder.matches(rawPassword, hashInDb);
        System.out.println("Does it match? " + matches);

        // Also: generate a fresh hash
        String newHash = encoder.encode(rawPassword);
        System.out.println("New hash: " + newHash);
    }
}