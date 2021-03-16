package com.example.demo.dtos;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.h2.util.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FcmDTO {

	public static class Builder{
		
		private String to;
		private String priority;
		private HashMap<String, String> data = new HashMap<String, String>(5);
		
		// DELETE THIS NOTIFICATION KEY(CHANGE NAME) AND ON ANDROID BUILD THE ENTIRE NOTIFICATION
		// SO WE TRIGGERS FCM noti ON BACKGROUND AND CAN START THE INTENT WE WANT
		private HashMap<String, String> notification = new HashMap<String, String>(3);
		
		public Builder(String title, String text) {
			super();
			notification.put("title", title);
			notification.put("text", text);
			notification.put("icon", "@drawable/ic_notification");
		}
		public Builder to(String to) {
			this.to = to;
			return this;
		}
		public Builder withImage(String url) {
			notification.put("image", url);
			return this;
		}
		public Builder withData(Object object, boolean allFields) {
			
	        for (Field field : object.getClass().getDeclaredFields()) {
	            if(allFields) field.setAccessible(true);
	            try {
	                data.put(field.getName(), field.get(object).toString());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
			return this;
		}
		public Builder andPriority(String priority) {
			this.priority = priority;
			return this;
		}
		public Builder andIcon(String icon) {
			notification.put("icon", icon);
			return this;
		}
		public FcmDTO build() {	
			return new FcmDTO(to,priority,data,notification);
		}
	}

	private String to;
	private String priority;
	private HashMap<String, String> data = new HashMap<String, String>(5);
	private HashMap<String, String> notification = new HashMap<String, String>(3);
	
	private FcmDTO() {}
	public FcmDTO(String to, String priority, HashMap<String, String> data, HashMap<String, String> notification) {
		super();
		this.to = to;
		this.priority = priority;
		this.data = data;
		this.notification = notification;
	}
	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public String getTo() {
		return to;
	}
	public String getPriority() {
		return priority;
	}
	public HashMap<String, String> getData() {
		return data;
	}
	public HashMap<String, String> getNotification() {
		return notification;
	}
	
	
	
}
