package com.SpringBoot.Mappers;

import org.mapstruct.Mapper;

import com.SpringBoot.dto.request.PermissionRequest;
import com.SpringBoot.dto.response.PermissionResponse;
import com.SpringBoot.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	
	Permission toEntity(PermissionRequest request);

	PermissionResponse toPermissionResponse(Permission permission);
}
