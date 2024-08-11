package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.SpringBoot.entities.Role;

public interface RoleServiceIF {
	
	public Optional<Role> createOne(Role role);
	
	public Optional<Role> getOne(String role_name);
	
	public Optional<List<Role>> getAllByRole_Name(Set<String> role_name_list);
	
	public List<Role> getAllRole();
}
