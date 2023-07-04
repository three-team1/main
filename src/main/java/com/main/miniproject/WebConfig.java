package com.main.miniproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/board/images/**").addResourceLocations("file:/C:/board/images/");
		registry.addResourceHandler("/review/images/**").addResourceLocations("file:/C:/review/images/");
	}
	
}
