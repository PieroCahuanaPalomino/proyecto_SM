package com.proyect.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyect.library.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}