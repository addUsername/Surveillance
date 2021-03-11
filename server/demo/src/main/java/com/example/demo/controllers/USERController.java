package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.enums.EnumStatus;
import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.dtos.HomeDTO;
import com.example.demo.dtos.PiDTO;
import com.example.demo.dtos.PiSettingsDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;
import com.example.demo.services.PushNotificationService;
import com.example.demo.services.UserService;

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
	private UserService us;
	@Autowired
	private PiService pis;	
	@Autowired
	private SimpMessagingTemplate messageSender;
	@Autowired
	private PushNotificationService pns;
	

	@RequestMapping(value = "/push/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> push(@PathVariable(value = "id") int id, @RequestPart("file") MultipartFile file, HttpServletRequest response ) throws UnknownHostException{
		//TODO 
		System.out.println("push to user");
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		messageSender.convertAndSend("/topic/"+id+"/","STREAM\n\nid="+id);
		
		pns.push(id,file,"http://"+InetAddress.getLocalHost().getHostAddress()+":8080");
		pis.changeStatus(id, EnumStatus.RUNNING);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping( value = "/stream/{id}") //update
    public void stream(@PathVariable(value = "id") int id,
    		HttpServletResponse response) throws IOException {
		
		String type = pis.getVideoExt(id);
		System.out.println(type.toString());
		if(type.equals(EnumVideoExt.MJPEG.toString())) {			
			response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
		}
		System.out.println("write stream");
	    fp.writeStream(response.getOutputStream(), id, type);	   
	    return;
    }
	
	@PostMapping(value = "/config/{id}") //TEST
	public ResponseEntity<?> config(@PathVariable(value = "id") int id, @Valid  @RequestBody PiSettingsDTO piSettings){
		if(pis.updatePiSettings(piSettings)) {
			return new ResponseEntity<String>("noice", HttpStatus.OK);
		};
		return new ResponseEntity<String>("bad :(", HttpStatus.BAD_REQUEST);		
	}
	
	@GetMapping(value = "/home") //OK
	public ResponseEntity<?> home(){
		HomeDTO home = us.getHome();
		if(home == null) return new ResponseEntity<String>("nope", HttpStatus.OK);
		return new ResponseEntity<HomeDTO>(home,HttpStatus.OK);
	}
	@PostMapping(value = "/setRpi") //OK
	public ResponseEntity<?> setRpi(@Valid @RequestBody PiDTO pi){
		if(pis.addRPi(pi)) {
			return new ResponseEntity<>("Rpi saved",HttpStatus.OK);
		};
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}	

	@RequestMapping(value = "/dump", method = RequestMethod.POST) //OK
	public ResponseEntity<?> dump(HttpServletResponse response){
		
		File dumpDb = auth.getDump();
		
		HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentType(new MediaType("application","octet-stream"));
		    respHeaders.setContentLength(dumpDb.length());
		    
		return new ResponseEntity<FileSystemResource>(
				new FileSystemResource( dumpDb), respHeaders, HttpStatus.OK);
	}	
}
