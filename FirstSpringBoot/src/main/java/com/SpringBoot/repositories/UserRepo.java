package com.SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, String>{

	Optional<Users> findByUsername(String username);
}
