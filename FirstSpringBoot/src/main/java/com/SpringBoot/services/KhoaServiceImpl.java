package com.SpringBoot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Khoa;
import com.SpringBoot.repositories.KhoaRepo;

@Service
public class KhoaServiceImpl implements KhoaServiceIF{
	@Autowired
	KhoaRepo repo;

	@Override
	public Optional<Khoa> getOne(Long id) {
		return repo.findById(id);
	}
	
	
}
