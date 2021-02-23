package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation. StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * TODO allow binary data, 30% on size reduction, check:
 * Rsocket, multipart request, streaming request
 * @author SERGI
 */

 /**
  * A litle bit of theory https://www.baeldung.com/spring-websockets-send-message-to-user and question +2 from https://stackoverflow.com/questions/22367223/sending-message-to-specific-user-on-spring-websocket
  
There are three main ways to say where messages are sent and how they are subscribed to using Spring WebSockets and STOMP:
Topics – common conversations or chat topics open to any client or user
Queues – reserved for specific users and their current sessions
Endpoints – generic endpoints

 And when your client subscribe to an channel start with /user/, eg: /user/queue/reply, your server instance will subscribe to a queue named queue/reply-user[session id]
  */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


	@Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
		/*
	    config.enableSimpleBroker("/topic",);//("/topic" , "/queue");
		// on client side subscribe("/{id}/queue/reply").. 
		// on controller simpMessagingTemplate.convertAndSendToUser(id_raspberry, "/queue/reply", message); // o el fichero settings.properties.. gut
	    config.setApplicationDestinationPrefixes("/app");
	    */
		config.enableSimpleBroker("/topic/");//, "/queue" ,"/user");
        config.setApplicationDestinationPrefixes("/app");
       // config.setUserDestinationPrefix("/user");
	  }
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {		
		registry.addEndpoint("/stream").setAllowedOrigins("*");//.withSockJS();
	}
	
	
}