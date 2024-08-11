package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Permission;
import com.SpringBoot.repositories.PermissionRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionServiceIF{
	
	PermissionRepo repo;

	@Override
	public Optional<Permission> saveOne(Permission permission) {
		return Optional.ofNullable(repo.save(permission));
	}

	@Override
	public Optional<Permission> getOneById(String permission_name) {
		return repo.findById(permission_name);
	}

	@Override
	public Optional<List<Permission>> getAllPerById(Set<String> listId) {
		return Optional.of(repo.findAllById(listId));
	}
	
	

}
