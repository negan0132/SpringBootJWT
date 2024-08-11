package com.SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Users;



@Repository
public interface UserRepo extends JpaRepository<Users, String>{

	Optional<Users> findByUsername(String username);
	
	@Query("SELECT u FROM Users u JOIN u.refreshID r WHERE r.refreshId =?1")
	Users getOneUserByRefreshToken(String refreshId);
}
