package com.example.demo.controllers;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.FileParser;

@RestController
@RequestMapping("/temp")
public class TEMPController {
	
	@Autowired
	private FileParser fp;

	@RequestMapping(value = "/push/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getPushImg (@PathVariable(name = "name") String name, HttpServletResponse response) {
		
		File img = fp.getPushImg(name);
		if(img == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentType(new MediaType("image","jpeg"));
		    respHeaders.setContentLength(img.length());
			    
		return new ResponseEntity<FileSystemResource>(
				new FileSystemResource( img), respHeaders, HttpStatus.OK
		);
	}
}
