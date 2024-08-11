package com.SpringBoot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Class;
import com.SpringBoot.repositories.ClassRepo;

@Service
public class ClassServiceImpl implements ClassServiceIF{

	@Autowired
	ClassRepo repo;
	
	@Override
	public Optional<Class> saveOne(Class clat) {
		return Optional.ofNullable(repo.save(clat));
	}

}
