package com.SpringBoot.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

@Service
public class loginService {
	
	protected static final String SIGN_KEY = "SoZWpw7VO9obK41fC6lH01YSbjeSJxw1tbXGSVvjBSdT7BmePnIi8WYlG/CxIjTv";

	public String getToken(String username) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
				.subject(username)
				.issuer("com.SpringBoot.Negan")
				.issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
				.claim("myclaim", "nothing")
				.build();
		Payload payload = new Payload(claimSet.toJSONObject());
		JWSObject token = new JWSObject(header, payload);
		try {
			token.sign(new MACSigner(SIGN_KEY.getBytes()));
			return token.serialize();
		} catch (KeyLengthException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (JOSEException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
