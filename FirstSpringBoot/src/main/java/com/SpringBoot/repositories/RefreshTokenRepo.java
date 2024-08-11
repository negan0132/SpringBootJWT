package com.SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String>{

	
}
