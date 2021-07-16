package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Deploying this to heroku at current state should work
 * @author SERGI
 *
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
	/**
	 * TODO reduce buffer size
	 * @return
	 */
	@Bean
	public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
	    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
	    container.setMaxTextMessageBufferSize(10000000); //10mb no disconect event called
	    container.setMaxBinaryMessageBufferSize(10000000); //10mb no disconect event called
	    System.err.println("Websocket factory returned");
	    return container;
	}
}
