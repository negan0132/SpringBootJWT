package com.SpringBoot.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SpringBoot.dto.request.RoleRequest;
import com.SpringBoot.dto.response.RoleReponse;
import com.SpringBoot.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	@Mapping(target="permissions", ignore = true)
	Role toEntity(RoleRequest request);
	
	RoleReponse toRoleResponse(Role role);
	
	List<RoleReponse> toListRoleResponse(List<Role> root);
}
