package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
	    container.setMaxTextMessageBufferSize(100000000); //100mb no disconect event called
	    container.setMaxBinaryMessageBufferSize(100000000); //100mb no disconect event called
	    System.err.println("Websocket factory returned");
	    return container;
	}
}
