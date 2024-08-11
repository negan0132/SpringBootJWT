package com.SpringBoot.services;

import java.util.Optional;

import com.SpringBoot.entities.Khoa;

public interface KhoaServiceIF {
	
	Optional<Khoa> getOne(Long id);
}
