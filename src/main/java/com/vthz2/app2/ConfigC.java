package com.vthz2.app2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigC implements WebMvcConfigurer{

	//@Override
	//public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//	Path imageDir=Paths.get("./user-photos");
	//	String imageDirPath=imageDir.toFile().getAbsolutePath();
	//	registry.addResourceHandler("/user-photos/**").addResourceLocations("file:/"+imageDirPath+"/");
	//}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry
	        .addResourceHandler("user-photos/**")
	        .addResourceLocations("file:/user-photos")
	        .setCachePeriod(0);
	}
	
}

