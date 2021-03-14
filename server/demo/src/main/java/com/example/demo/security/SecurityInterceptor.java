package com.example.demo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.services.UserService;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private Jwt jwt;
	
	@Autowired
	private UserService us;
	
	/*
	 * TODO: throw NullException and/or invalida user/pass exception
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		if(token == null) {
			 token = request.getHeader("authorization");
		}
		token = token.replace("bearer ", "");
		String username = us.getUsername();
		
		if(token == null || username == null) return false;		
		return (jwt.isValid(token, username));
	}
}
