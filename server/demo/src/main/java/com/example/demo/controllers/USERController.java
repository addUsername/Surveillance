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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.dtos.HomeDTO;
import com.example.demo.dtos.PiDTO;
import com.example.demo.dtos.PiSettingsDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;
import com.example.demo.services.UserService;

/**
 * TODO Settings and Account things
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
		
	
	@CrossOrigin
	@GetMapping( value = "/stream/{id}") //????????????????????????
    public void stream(@PathVariable(value = "id") int id,
    		HttpServletResponse response) throws IOException {
		
		System.out.println("STREREEEAAAAMMM");
		
		String type = pis.getVideoExt(id);
		if(type.equals(EnumVideoExt.MJPEG.toString())) {			
			response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
		}
	    fp.writeStream(response.getOutputStream(), id, type);	   
	    return;
    }
	@GetMapping(value = "/config/{id}")
	public ResponseEntity<?> getConfig(@PathVariable(value = "id") int id){
		
		PiSettingsDTO dto = pis.getRpiSettings(id);
		if(dto != null) {
			return new ResponseEntity<PiSettingsDTO>(dto, HttpStatus.OK);
		};
		return new ResponseEntity<String>("bad :(", HttpStatus.BAD_REQUEST);		
	}
	
	@PostMapping(value = "/config/add") //TEST
	public ResponseEntity<?> config(@Valid  @RequestBody PiSettingsDTO piSettings){
		if(pis.updatePiSettings(piSettings)) {
			return new ResponseEntity<String>("noice", HttpStatus.OK);
		};
		return new ResponseEntity<String>("bad :(", HttpStatus.BAD_REQUEST);		
	}
	
	@RequestMapping(value = "/upload/{extension}/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMediaFromRPi(@PathVariable(value = "id") int id,@PathVariable(value = "extension") String extension) {
		
		System.out.println("upload");		
		String command;
		if(extension.equalsIgnoreCase("jpg")) {
			fp.iniUploadFile(id);
			command = "SCREENSHOT";
		}else if(extension.equalsIgnoreCase("h264")){
			command = "STREAM";
			fp.iniUpload(id);
		}else {
			return new ResponseEntity<String>("wrong ext.",HttpStatus.BAD_REQUEST);
		}
		messageSender.convertAndSend("/topic/"+id+"/",command+"\n\nid="+id);		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET) //ok
	public ResponseEntity<?> download(@PathVariable(value = "id") int id) {
		
		System.out.println("upload");
		byte[] file =fp.downloadFile(id);
		
		if(file == null) return new ResponseEntity<>(HttpStatus.TOO_EARLY);
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(new MediaType("image","jpeg"));
		    
		return new ResponseEntity<byte[]>(file, respHeaders, HttpStatus.OK);		
	}
	/**
	 * To see if jwt token is still valid and auto-login
	 * TODO
	 * To see if jwt token is still valid and auto-login? YEAH!
	 * 
	 * @return
	 */
	@GetMapping(value = "/login") //TODO not used yet, should allow login if jwt is present?
	public ResponseEntity<?> loginReusing(){
		return new ResponseEntity<String>(HttpStatus.OK);
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
