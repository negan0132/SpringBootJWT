package com.SpringBoot.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Permission;
import com.SpringBoot.entities.Role;
import com.SpringBoot.entities.Users;
import com.SpringBoot.repositories.InvalidTokenRepo;
import com.SpringBoot.repositories.UserRepo;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserServiceIF{
	
	UserRepo repo;
	
	InvalidTokenRepo invalidTokenRepo;
	
	@Value("${security.jwt.secret}")
	@NonFinal
	String SCRET_KEY;

	@Override
	public Optional<Users> createOne(Users user) {
		return Optional.ofNullable(repo.save(user));
	}

	@Override
	public Optional<Users> getById(String id) {
		return repo.findById(id);
	}

	@Override
	public Users getByUserName(String user_name) {
		return repo.findByUsername(user_name).orElseThrow(()-> new RuntimeException("User not found"));
	}
	
	@Override
	public String getUserToken(Users user) {
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		JWTClaimsSet body = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issueTime(new Date())
				.issuer("com.SpringBoot")
				.expirationTime(new Date(
						Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
					))
				.claim("scope", getRole(user.getRoles()))
				.jwtID(UUID.randomUUID().toString())
				.build();
		Payload payLoad = new Payload(body.toJSONObject());
		JWSObject objectt = new JWSObject(header,payLoad);
		try {
			objectt.sign(new MACSigner(SCRET_KEY.getBytes()));
		} catch (JOSEException e) {
			throw new RuntimeException("Can not create Token");
		}
		
		return objectt.serialize();
	}
	
	public String getRole(Set<Role> roles) {
		StringJoiner linker = new StringJoiner(" ");
		Set<String> roleNPermission = new HashSet<String>();
		for (Role role : roles) {
			roleNPermission.add(role.getRole_name());
			for (Permission per : role.getPermissions()) {
				roleNPermission.add(per.getPermission_name());
			}
		}
		for (String string : roleNPermission) {
			linker.add(string);
		}
		return linker.toString();
	}

	@Override
	public SignedJWT verifyOneToken(String token) throws JOSEException, ParseException {
		JWSVerifier verifier = new MACVerifier(SCRET_KEY.getBytes());
		SignedJWT signedJwt = SignedJWT.parse(token);
		if(!signedJwt.verify(verifier) 
				|| !(signedJwt.getJWTClaimsSet().getExpirationTime().after(new Date()))
				|| invalidTokenRepo.findById(signedJwt.getJWTClaimsSet().getJWTID()).isPresent()) {
			throw new RuntimeException("Token not verify !");
		}
		return signedJwt;
	}

}
