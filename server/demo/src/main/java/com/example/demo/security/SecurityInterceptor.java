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
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization").replace("bearer ", "");
		String username = us.getUsername();
		
		if(token == null || username == null) return false;		
		if(jwt.isValid(token, username)) return true;
		
		return false;
	}
}
