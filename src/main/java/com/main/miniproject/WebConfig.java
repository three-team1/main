package com.main.miniproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Value("${user.dir}/src/main/resources/static/images/")
	String itemImgPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/board/images/**").addResourceLocations("file:/C:/board/images/");
		registry.addResourceHandler("/review/images/**").addResourceLocations("file:/C:/review/images/");
		registry.addResourceHandler("/review/resized/**").addResourceLocations("file:/C:/review/resized/");
		registry.addResourceHandler("/src/main/resources/static/images/**").addResourceLocations("file:" + itemImgPath);
	}

}