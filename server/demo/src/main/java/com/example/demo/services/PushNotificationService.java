package com.example.demo.services;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PushNotificationService {

	@Autowired
	AuthService auth;
	@Autowired
	FileParser fp;
	
	@Value("${secret.pushToken}")
	private String pushToken;
	@Value("${path.firebase}")
	private String URL;
	private Set<String> activePushImageEndpoints = new HashSet();
	
	/**
	 * 
	 * 
	 * @param id RPi id that sends the notification
	 * @param file Image of the notification
	 * @param urlPath Hostname uri to generate 1 use link
	 */
	@Async("asyncExecutor")
	public void push(int id, MultipartFile file, String urlPath) {
		

		// TODO Auto-generated method stub
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key="+pushToken);
        Body body = new Body();
        body.to = auth.getFcmtToken();
        body.body = "Rpi :"+id+"!";
        body.title = "Alert!";
        String rd = randStr();
        activePushImageEndpoints.add(rd);
        body.image = urlPath + "/temp/push/"+ rd;
        HttpEntity<String> request = new HttpEntity<>(body.toJsonString(), headers);
        try {
			fp.savePushImage(rd,file.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			body.image="";
		}
        ResponseEntity<String> response = rt.exchange(URL, HttpMethod.POST, request, String.class);
        System.out.println("response push noti: "+response.getBody());
		
	}
	/**
	 * This methods sets the validity of /temp/push/{name}
	 * @param name
	 * @return
	 */
	public Boolean isImgAccesible(String name) {
		boolean bol = activePushImageEndpoints.contains(name);
		//if(bol) activePushImageEndpoints.remove(name);
		return bol;
	}
	/**
	 * Generate random img name to use as temp img on FCM
	 * @return
	 */
	private String randStr() {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < 10; i++) {
	       int randIndex=rand.nextInt(aToZ.length()); 
	       res.append(aToZ.charAt(randIndex));            
	    }
	    return res.toString();
	}
	/**
	 * Represents the body of the request to FCM
	 * @author SERGI	 *
	 */
	private class Body{
		
		String to, notification, body, title, image;
		
		Body(){}
		// lel
		String toJsonString() {
			String toReturn = "{\r\n"
			+ "    \"to\" : \""+to+"\",\r\n"
			+ "    \"notification\" : {\r\n"
			+ "        \"body\" : \""+body+"\",\r\n"
			+ "        \"title\" : \""+title+"\",\r\n"
			+ "        \"image\": \""+image+"\"}\r\n}";
			
			return toReturn;
			
		}
		
		
		
	}

}
