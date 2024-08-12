package com.SpringBoot.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SpringBoot.dto.request.UserRequest;
import com.SpringBoot.dto.response.UserResponse;
import com.SpringBoot.entities.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "refreshID", ignore = true)
	Users toEntity(UserRequest request);
	
	UserResponse toUserResponse(Users user);
}
