package com.example.demo.controllers;

import java.io.File;

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

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.services.AuthService;

@RestController
public class AUTHController {
	
	@Autowired
	private AuthService auth;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseEntity<String> login(
			@RequestPart("pin") int pin,
			@RequestPart("data") MultipartFile file){
	
		
		/* parse file
		 * cipher
		 * build up h2
		 * 
		 * if(!auth.checkPin(pin)) {
		 * return new ResponseEntity(new String("ok"), HttpStatus.UNAUTHORIZED);
		 * }
		 * 
		 */
		return new ResponseEntity(new String("ok"), HttpStatus.OK); // redirect home
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register (@Valid @RequestBody RegisterDTO newUser){
		
		if(auth.registerUser(newUser)) {
			File dumpDb = auth.getDump();
			
			HttpHeaders respHeaders = new HttpHeaders();
				respHeaders.setContentType(new MediaType("application","octet-stream"));
			    respHeaders.setContentLength(dumpDb.length());
			    
			return new ResponseEntity<FileSystemResource>(
			new FileSystemResource( dumpDb), respHeaders, HttpStatus.OK
			);
		}
		
		return new ResponseEntity("user already exist", HttpStatus.BAD_REQUEST); // redirect login
	}
	

}
