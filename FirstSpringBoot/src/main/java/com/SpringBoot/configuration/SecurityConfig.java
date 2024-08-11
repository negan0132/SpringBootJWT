package com.SpringBoot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomJwtDecoder customDecoder;
	
	private final String[] publicEntryPoint = {"/user/add", "/login", "/introspect", "/log-out"};
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(request -> 
        	request.requestMatchers(HttpMethod.POST, publicEntryPoint).permitAll()
        	.requestMatchers(HttpMethod.GET, "/student/all").hasAuthority("ROLE_ADMIN")
        	.anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2 -> 
        	oauth2.jwt(jwtConfig -> 
        	jwtConfig.decoder(customDecoder)
        			.jwtAuthenticationConverter(jwtConverter())));
        return httpSecurity.build();
    }

//	@Bean
//	NimbusJwtDecoder jwtDecoder() {
//		SecretKeySpec keySpect = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
//		return NimbusJwtDecoder.withSecretKey(keySpect).macAlgorithm(MacAlgorithm.HS512).build();
//	}
	
	@Bean
	JwtAuthenticationConverter jwtConverter() {
		JwtGrantedAuthoritiesConverter grant = new JwtGrantedAuthoritiesConverter();
		grant.setAuthorityPrefix("");
		JwtAuthenticationConverter convert = new JwtAuthenticationConverter();
		convert.setJwtGrantedAuthoritiesConverter(grant);
		return convert;
	}
}
