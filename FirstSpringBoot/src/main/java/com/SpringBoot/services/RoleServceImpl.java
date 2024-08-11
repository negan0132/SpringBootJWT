package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Role;
import com.SpringBoot.repositories.RoleRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServceImpl implements RoleServiceIF{
	
	RoleRepo repo;

	@Override
	public Optional<Role> createOne(Role role) {
		return Optional.ofNullable(repo.save(role));
	}

	@Override
	public Optional<Role> getOne(String role_name) {
		return repo.findById(role_name);
	}

	@Override
	public Optional<List<Role>> getAllByRole_Name(Set<String> role_name_list) {
		return Optional.ofNullable(repo.findAllByRole_name(role_name_list));
	}
	
	@Override
	public List<Role> getAllRole(){
		return Optional.of(repo.findAll()).orElseThrow(()-> new RuntimeException("No data available"));
	}
	

}
