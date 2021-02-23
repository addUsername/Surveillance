package com.example.demo.configs;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

/**
 * TODO log this events
 * @author SERGI
 *
 */
@Component
public class WebSocketEventListener {
	/*
	@EventListener
	public void aa(AbstractSubProtocolEvent event) {
		System.out.println(event.getUser());
		System.out.println(event.getSource().toString());
		System.out.println(event.getMessage().toString());
	}
	*/
  @EventListener
  public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
      GenericMessage<?> message = (GenericMessage) event.getMessage();
      String simpDestination = (String) message.getHeaders().get("simpDestination");
      
      System.err.println("sub event!!");
      System.out.println(simpDestination);
  }
  @EventListener
  public void handleSessionConnectedEvent(SessionConnectEvent event) {
      GenericMessage<?> message = (GenericMessage) event.getMessage();
      
      System.err.println("connect event!!");
      System.out.println(SimpAttributesContextHolder.currentAttributes().getSessionId());
      System.out.println(event.getSource());
      System.out.println(message);
  }
  @EventListener
  public void handleSessionDisonnectedEvent(SessionDisconnectEvent event) {
      GenericMessage<?> message = (GenericMessage) event.getMessage();

      System.err.println("Disconnect event!!");
      System.out.println(event.getSource());
      System.out.println(message);
  }
  
}