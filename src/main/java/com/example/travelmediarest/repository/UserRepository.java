package com.example.travelmediarest.repository;

import com.example.travelmediarest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMail(String mail);

}
