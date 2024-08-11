package com.SpringBoot.controller;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.dto.request.LoginRequest;
import com.SpringBoot.dto.request.TokenRequest;
import com.SpringBoot.dto.response.LoginResponse;
import com.SpringBoot.dto.response.TokenResponse;
import com.SpringBoot.entities.InvalidToken;
import com.SpringBoot.entities.RefreshToken;
import com.SpringBoot.entities.Users;
import com.SpringBoot.repositories.InvalidTokenRepo;
import com.SpringBoot.services.RefreshTokenServiceIF;
import com.SpringBoot.services.UserServiceIF;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityController {

	UserServiceIF userService;

	InvalidTokenRepo invalidRepo;

	RefreshTokenServiceIF refreshTokenService;

	@NonFinal
	@Value("${security.jwt.secret}")
	String SCRET_KEY;

	@SuppressWarnings("static-access")
	@PostMapping("/login")
	public LoginResponse loginApplication(@RequestBody LoginRequest request) {
		Users root_user = userService.getByUserName(request.getUser_name());
		PasswordEncoder pe = new BCryptPasswordEncoder(10);
		if (pe.matches(request.getPassword(), root_user.getPassword())) {
			if (root_user.getRefreshID() != null && root_user.getRefreshID().getExpirationDate().after(new Date())) {
				return new LoginResponse().builder().authenticated(true).token(userService.getUserToken(root_user))
						.build();
			} else {
				RefreshToken refreshToken = new RefreshToken().builder().token(UUID.randomUUID().toString())
						.expirationDate(new Date(Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()))
						.user(root_user).build();
				root_user.setRefreshID(refreshToken);
				refreshTokenService.createOne(refreshToken);
				return new LoginResponse().builder().authenticated(true).token(userService.getUserToken(root_user))
						.build();
			}
		}
		return new LoginResponse().builder().authenticated(false).token("Do not have Token").build();
	}

	@PostMapping("/introspect")
	public ResponseEntity<?> verifyToken(@RequestBody TokenRequest request) {
		try {
			userService.verifyOneToken(request.getToken());
			return ResponseEntity.ok(new TokenResponse().builder().checked(true).message("Token was verified").build());
		} catch (JOSEException | ParseException e) {
			throw new RuntimeException("Token not verify !");
		}
	}

	@PostMapping("/log-out")
	public ResponseEntity<String> logoutToken(@RequestBody TokenRequest request) {
		try {
			SignedJWT signed = userService.verifyOneToken(request.getToken());
			InvalidToken invalid = new InvalidToken().builder().jwtID(signed.getJWTClaimsSet().getJWTID())
					.expriryTime(signed.getJWTClaimsSet().getExpirationTime()).build();
			invalidRepo.save(invalid);
			return ResponseEntity.ok("Log out success");
		} catch (JOSEException | ParseException e) {
			throw new RuntimeException("Token invalid");
		}
	}
}
