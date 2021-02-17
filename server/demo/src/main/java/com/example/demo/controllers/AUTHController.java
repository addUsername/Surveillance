package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dtos.PinDTO;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AUTHController {
	
	@Autowired
	private AuthService auth;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseEntity<String> login(
			@Valid @RequestPart("pin") PinDTO pin,
			@RequestPart("data") MultipartFile file) throws IOException{
	
		System.out.println(pin.getPin());
		System.out.println(file.getSize());
		System.out.println(file.getBytes());
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		Files.copy(file.getInputStream(), new File("src/main/resources/dump2.sql").toPath(), StandardCopyOption.REPLACE_EXISTING);
		File data = auth.validateAndDecryptData(file);
		
		if(auth.validate(pin)) {
			String jwt = auth.generateToken();
			return new ResponseEntity(jwt, HttpStatus.OK);
		}
		/* parse file
		 * cipher
		 * build up h2
		 * return JWT token
		 * 
		 * if(!auth.checkPin(pin)) {
		 * return new ResponseEntity(new String("ok"), HttpStatus.UNAUTHORIZED);
		 * }
		 * 
		 */
		 // redirect home
		return new ResponseEntity(new String("ooops"), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register (@Valid @RequestBody RegisterDTO newUser, HttpServletResponse response) throws IOException{
		
		if(auth.registerUser(newUser)) {
			//response.sendRedirect("/auth/login"); // redirect login
			return new ResponseEntity("ok", HttpStatus.OK); 
		}		
		return new ResponseEntity("user already exist", HttpStatus.BAD_REQUEST); 
	}

}
