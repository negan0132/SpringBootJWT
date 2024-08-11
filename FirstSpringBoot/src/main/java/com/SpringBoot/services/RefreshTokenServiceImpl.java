package com.SpringBoot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBoot.entities.RefreshToken;
import com.SpringBoot.repositories.RefreshTokenRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenServiceImpl implements RefreshTokenServiceIF{

	RefreshTokenRepo repo;
	@Override
	public RefreshToken createOne(RefreshToken refreshToken) {
		return Optional.of(repo.save(refreshToken)).orElseThrow(()-> new RuntimeException("Error when create Refresh Token"));
	}
	
}
