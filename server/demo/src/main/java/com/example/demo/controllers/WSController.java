package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;

import com.example.demo.model.FileParser;

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
	
	@MessageMapping("/mjpeg")
	public void getFrames(Message<String> string) throws UnsupportedEncodingException {
		
		byte[] decoded = Base64Utils.decodeFromString(string.getPayload());
		fp.add(decoded);
		smt.convertAndSend("/topic/info", "200");
	}
	
}
