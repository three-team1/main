package com.main.miniproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value("${reviewImgLocation}")
	String reviewImgPath;

	@Value("${reviewResizedLocation}")
	String reviewResizedPath;

	@Value("${boardImgLocation}")
	String boardImgPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/board/images/**").addResourceLocations("file:" + boardImgPath + "/");
		registry.addResourceHandler("/review/images/**").addResourceLocations("file:" + reviewImgPath);
		registry.addResourceHandler("/review/resized/**").addResourceLocations("file:" + reviewResizedPath);
	}

}