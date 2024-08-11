package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.SpringBoot.entities.Permission;

public interface PermissionServiceIF {
	
	public Optional<Permission> saveOne(Permission permission);
	
	public Optional<Permission> getOneById(String permission_name);
	
	public Optional<List<Permission>> getAllPerById(Set<String> listId);
	
}