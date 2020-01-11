package org.neurobrain.tlozbotw.security;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
 
@Component
public class JwtProvider {
 
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${app.auth.jwt-secret}")
	private String jwtSecret;
 
	@Value("${app.auth.jwt-expiration}")
	private int jwtExpiration;


	public String generateJwtToken(Authentication authentication) {
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		return Jwts.builder()
			.setSubject((userPrincipal.getUsername()))
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}
 
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
 
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty");
		}

		return false;
	}
	
}
