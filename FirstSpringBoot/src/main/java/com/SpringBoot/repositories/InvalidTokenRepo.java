package com.SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.entities.InvalidToken;

public interface InvalidTokenRepo extends JpaRepository<InvalidToken, String>{
	
}
