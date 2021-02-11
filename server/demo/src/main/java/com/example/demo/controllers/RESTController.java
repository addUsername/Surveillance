package com.example.demo.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FileParser;

/**
 * This could be in another microservice, making a total of 3, like a triangle REST - FP - WEBSOCKET
 * @author SERGI
 *
 */
@RestController
public class RESTController {
	
	@Autowired
	private FileParser fp;
	
	@GetMapping("ini/{id}")
	public ResponseEntity<String> hi(@PathVariable(value = "id") int id){
		//id
		System.out.println("ini");
		fp.iniUpload(id);
		return new ResponseEntity( HttpStatus.ACCEPTED);
	}
	/*
	@GetMapping("save")
	public ResponseEntity<String> save(){
		System.out.println("SAAAAAVE");
		return new ResponseEntity( (fp.save())? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	@CrossOrigin
	@GetMapping( value = "/stream/{id}")
    public void stream(@PathVariable(value = "id") int id,
    		HttpServletResponse response) throws IOException {
		/*
		 * procesar token usuario (android/web)
		 * 
		 * 
		 * */
	   //response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
	   fp.writeStream(response.getOutputStream(), id, ".h264");
	   
	   return;
    }
}
