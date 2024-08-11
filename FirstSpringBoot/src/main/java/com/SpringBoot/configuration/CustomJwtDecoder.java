package com.SpringBoot.configuration;

import java.text.ParseException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.SpringBoot.services.UserServiceIF;
import com.nimbusds.jose.JOSEException;

@Component
public class CustomJwtDecoder implements JwtDecoder {
	@Value("${security.jwt.secret}")
	private String signerKey;

	@Autowired
	private UserServiceIF userService;

	private NimbusJwtDecoder nimbusJwtDecoder = null;

	@Override
	public Jwt decode(String token) throws JwtException {

		try {
			userService.verifyOneToken(token);
		} catch (JOSEException | ParseException | RuntimeException e) {
			throw new JwtException("Token not valid");
		}

		if (Objects.isNull(nimbusJwtDecoder)) {
			SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
			nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
		}

		return nimbusJwtDecoder.decode(token);
	}
}