package com.suchit.jwt.repository;

import com.suchit.jwt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
