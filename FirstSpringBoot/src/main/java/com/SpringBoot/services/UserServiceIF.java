package com.SpringBoot.services;

import java.text.ParseException;
import java.util.Optional;

import com.SpringBoot.entities.Users;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

public interface UserServiceIF {
	public Optional<Users> createOne(Users user);
	
	public Optional<Users> getById(String id);
	
	public Users getByUserName(String user_name);
	
	public String getUserToken(Users user);
	
	public SignedJWT verifyOneToken(String token) throws JOSEException, ParseException;
	
}
