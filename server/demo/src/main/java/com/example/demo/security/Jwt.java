package com.example.demo.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author SERGI
 *
 */
@Component
public class Jwt {
	
	@Value("${secret.jwt}")
	private String secret;
	private static final int EXPIRATION = 3 * 60 * 60;
	private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

	public String generateToken(String string) {
		return Jwts.builder().setSubject(string).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() * EXPIRATION)).signWith(ALGORITHM, secret).compact();
	}
	
	public Boolean isValid(String token, String username) {

		try {
			return (username.equals(getSubject(token)) && new Date().before(getExpiration(token)));	
		}
		catch (Exception e) {
			System.out.println("token malformed");
			return false;
		}
	}
	
	public String getSubject(String token) throws Exception {
		
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();
	}
	public Date getExpiration(String token) throws Exception {
		
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getExpiration();
	}
}
