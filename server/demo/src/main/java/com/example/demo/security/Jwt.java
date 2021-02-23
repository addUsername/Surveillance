package com.example.demo.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author SERGI
 *
 */
public class Jwt {
	
	private static final int EXPIRATION = 3 * 60 * 60;
	private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

	/**
	 * This jwt has a subject (it is a "special claim"), issue and expiration
	 * days, @link {@link JwtHandler#ALGORITHM} and the @link
	 * {@link JwtHandler#secret} key
	 *
	 * @param user
	 * @return
	 */
									//GUARDAR SU USERNAME EN EL SUBJECT NO EL PIN!!
	public static String generateToken(byte[] secret, String string) {
		
		return Jwts.builder().setSubject(string).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() * EXPIRATION)).signWith(ALGORITHM, secret).compact();
	}

	/**
	 * A lot of things could be wrong, finally we just care about if the token
	 * subject equals to username, that means that the user had signed up not long
	 * ({@link JwtHandler#EXPIRATION} before.
	 *
	 * @param token
	 * @param username
	 * @return
	 */
	public static Boolean isValid(byte[] secret, String token, String username) {

		if (username.equals(Jwt.getSubject(secret, token)))
			return true;
		return false;
	}

	public static String getSubject(byte[] secret, String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
}
