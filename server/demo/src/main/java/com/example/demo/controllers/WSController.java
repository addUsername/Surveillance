package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;

import com.example.demo.services.FileParser;

/**
 * Pi code needs to implement recived but not requested commands (apply config, status, on/off..) this operations
 * will be enable only when PI is not streaming, in /config maybe
 * @author SERGI
 *
 */
@Controller
public class WSController {

	@Autowired
	private SimpMessagingTemplate smt;
	@Autowired
	private FileParser fp;
	
	@MessageMapping("/stream/{id}")
	public void getFrames(@DestinationVariable String id, Message<String> string) throws UnsupportedEncodingException {
		
		System.out.println(id);
		
		byte[] decoded = Base64Utils.decodeFromString(string.getPayload());
		//byte[] decoded = Base64Utils.decodeFromString(new String(string.getContent().getBytes()));
		fp.add(decoded, Integer.parseInt(id));
		//smt.convertAndSendToUser(""+id, "/queue/reply", "hi bitchess, I'm from android this f shit works!! aa");
		//smt.convertAndSendToUser(string.getToUser(), "/queue/reply", "hiiiiii");
		//smt.convertAndSend("/topic/info/", "200");
	}
	/* to report changes status to server, prob not needed */
	@MessageMapping("/string/{id}")
	public void getString(@DestinationVariable String id, Message<String> string) throws UnsupportedEncodingException {
		
		System.out.println(id);
		System.out.println(string.getPayload());
	}
	/*
	@SubscribeMapping("/info")
	public String sendOneTimeMessage() {
		System.out.println("aaaaaaaaaaaah");
		return "server one-time message via the application";
	}*/
}
