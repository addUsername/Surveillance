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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.PiDTO;
import com.example.demo.dtos.PinDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;

/**
 * TODO ALL OF THIS NEED TO BE AUTHENTICATED VIA JWT
 * @author SERGI
 *
 */
@RestController
@RequestMapping("/user")
public class USERController {
	
	@Autowired
	private FileParser fp;

	@Autowired
	private AuthService auth;
	@Autowired
	private PiService pis;
	
	@Autowired
	private SimpMessagingTemplate messageSender;
	
	@GetMapping(value = "/setRpi")
	public ResponseEntity<?> setRpi(@Valid PiDTO pi){
		if(pis.addRPi(pi)) {
			return new ResponseEntity<>("Rpi saved",HttpStatus.OK);
		};
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@GetMapping( value = "/stream/{id}")
    public void stream(@PathVariable(value = "id") int id,
    		HttpServletResponse response) throws IOException {
		
	   //response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
	   fp.writeStream(response.getOutputStream(), id, ".h264");	   
	   return;
    }

	@RequestMapping(value = "/dump", method = RequestMethod.POST)
	public ResponseEntity<?> dump(HttpServletResponse response){
		
		File dumpDb = auth.getDump();
		
		HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentType(new MediaType("application","octet-stream"));
		    respHeaders.setContentLength(dumpDb.length());
		    
		return new ResponseEntity<FileSystemResource>(
				new FileSystemResource( dumpDb), respHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sayHello")
	public ResponseEntity<?> sayHello(){
		messageSender.convertAndSendToUser("1", "/queue/reply", "hi bitchess, I'm from android this f shit works!! aa");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
