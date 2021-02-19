package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;

@RestController
@RequestMapping("/pi")
public class PIController {

	@Autowired
	FileParser fp;
	@Autowired
	PiService pis;
	
	@GetMapping("/ini/{id}")
	public ResponseEntity<String> hi(@PathVariable(value = "id") int id){
		
		String file = pis.getSettingsFile(Long.valueOf(id));
		
		if(file == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		
		fp.iniUpload(id);
		return new ResponseEntity<String>(file,HttpStatus.ACCEPTED);
	}
}
