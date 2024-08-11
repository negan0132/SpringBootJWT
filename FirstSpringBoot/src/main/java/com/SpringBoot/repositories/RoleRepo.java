package com.SpringBoot.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Role;


@Repository
public interface RoleRepo extends JpaRepository<Role, String>{
	
	@Query("FROM Role r WHERE r.role_name IN ?1")
	List<Role> findAllByRole_name(Set<String> role_name);
}
