package com.SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Permission;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, String>{
	
}
