package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
		if(auth.validateAndDecryptData(file)) {
			auth.loadDb();
			
			if(auth.checkPin(pin.getPin())) {
				String jwt = auth.generateToken(pin.getPin());
				return new ResponseEntity(jwt, HttpStatus.OK);
			}			
		}
		
		return new ResponseEntity(new String("ooops"), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register (@Valid @RequestBody RegisterDTO newUser, HttpServletResponse response) throws IOException{
		
		if(auth.registerUser(newUser)) {
			File dumpDb = auth.getDump();
			
			HttpHeaders respHeaders = new HttpHeaders();
				respHeaders.setContentType(new MediaType("application","octet-stream"));
			    respHeaders.setContentLength(dumpDb.length());
			    
			return new ResponseEntity<FileSystemResource>(
					new FileSystemResource( dumpDb), respHeaders, HttpStatus.OK
			); 
		}		
		return new ResponseEntity("user already exists", HttpStatus.BAD_REQUEST); 
	}
	

}
