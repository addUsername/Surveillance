package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;

/**
 * This controller is used to get the image on the push notification and for
 * consume streaming in Android by WebView/fetch/jmuxer. So we can't (and should not) use jwt
 * 
 * Implement some kind of auth?, the data only last 1 request, but new data arrives on fixed.
 * include, at leat, some kind of token as URL parameter (or path), to make unique links.
 * @author SERGI
 *
 */
@RestController
@RequestMapping("/temp")
public class TEMPController {
	
	@Autowired
	private FileParser fp;
	@Autowired
	private PiService pis;
	/**
	 * Serves img to FCM
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/push/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getPushImg (@PathVariable(name = "name") String name) {
		
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
	/**
	 * Serves stream to js on a WebView
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@CrossOrigin
	@GetMapping( value = "/stream/{id}") //update
    public void stream(@PathVariable(value = "id") int id,
    		HttpServletResponse response) throws IOException {
		
		String type = pis.getVideoExt(id);
		if(type.equals(EnumVideoExt.MJPEG.toString())) {
			System.out.println("MJPEG");
			response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
		}else {
			response.setContentType("application/octet-stream");
		}
		System.out.println("write stream");
	    fp.writeStream(response.getOutputStream(), id, type);	   
	    return;
    }


}
