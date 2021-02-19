package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	
	@MessageMapping("/mjpeg/{id}")
	public void getFrames(@DestinationVariable String id, Message<String> string) throws UnsupportedEncodingException {
		
		System.out.println(id);
		
		byte[] decoded = Base64Utils.decodeFromString(string.getPayload());
		fp.add(decoded, Integer.parseInt(id));
		smt.convertAndSend("/topic/info", "200");
	}
}
