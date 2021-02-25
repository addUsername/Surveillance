package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;

import com.example.demo.domain.enums.EnumStatus;
import com.example.demo.services.FileParser;
import com.example.demo.services.PiService;

/**
 * Pi code needs to implement recived but not requested commands (apply config, status, on/off..) this operations
 * will be enable only when PI is not streaming, in /config maybe
 * @author SERGI
 *
 */
@Controller
public class WSController {
	

	@Autowired
	private FileParser fp;
	@Autowired
	private PiService pis;
	
	@MessageMapping("/stream/{id}")
	public void getFrames(@DestinationVariable String id, Message<String> string) throws UnsupportedEncodingException {
		
		System.out.println(id);
		
		byte[] decoded = Base64Utils.decodeFromString(string.getPayload());
		fp.add(decoded, Integer.parseInt(id));
	}
	/* to report changes status to server, prob not needed */
	@MessageMapping("/string/{id}")
	public void getString(@DestinationVariable int id, Message<String> string) throws UnsupportedEncodingException {
		
		if(string.getPayload().contains("STATUS")) {
			String status = string.getPayload().split("STATUS\n\n")[1];
			pis.changeStatus(id,EnumStatus.valueOf(status));
		}
	}
	/*
	@SubscribeMapping("/info")
	public String sendOneTimeMessage() {
		System.out.println("aaaaaaaaaaaah");
		return "server one-time message via the application";
	}*/
}
