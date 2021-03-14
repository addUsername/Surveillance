package com.example.demo.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.enums.EnumStatus;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;
import com.example.demo.services.PushNotificationService;

@RestController
@RequestMapping("/pi")
public class PIController {
	//TODO Add Token for rpi 
	@Autowired
	FileParser fp;
	@Autowired
	PiService pis;
	@Autowired
	private SimpMessagingTemplate messageSender;
	@Autowired
	private PushNotificationService pns;
	
	@GetMapping("/ini/{id}")
	public ResponseEntity<String> ini(@PathVariable(value = "id") int id){
		
		String file = pis.getSettingsFile(Long.valueOf(id));
		
		if(file == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		
		if(pis.changeStatus(id, EnumStatus.UP)) {
			fp.iniUpload(id);
			return new ResponseEntity<String>(file,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/push/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> push(@PathVariable(value = "id") int id, @RequestPart("file") MultipartFile file ) throws UnknownHostException{
		//TODO 
		System.out.println("push to user");
		System.out.println(file.getName());
		System.out.println(file.getSize());
		
		messageSender.convertAndSend("/topic/"+id+"/","STREAM\n\nid="+id);		
		pns.push(id,file,"http://"+InetAddress.getLocalHost().getHostAddress()+":8080");
		pis.changeStatus(id, EnumStatus.RUNNING);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/screenshot/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> postScreensot(@PathVariable(value = "id") int id, @RequestPart("file") MultipartFile file ) throws IOException{
		//TODO 
		System.out.println("pi screenshot");
		System.out.println(file.getName());
		System.out.println(file.getSize());
			
		fp.saveScreenshot(id,file);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
