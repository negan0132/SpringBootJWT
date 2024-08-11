package com.SpringBoot.dto.request;

import java.util.Set;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
	String username;
	String password;
	String email;
	String phone;
	Set<String> roles;
}
