package com.vthz2.app2;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/user-photos")
public class StaticFileController {
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	@ResponseBody
	@RequestMapping(value = "/{fileName}",  produces = "image/jpeg")
	public Resource texture(@PathVariable("fileName") String fileName) {
		System.out.println("I am called"+ fileName);
		System.out.println("classpath");
	    return resourceLoader.getResource("classpath:userImages/" + fileName);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getimage/{fileName}",  produces = "image/jpeg")
	public Resource returnProfileImage(@PathVariable("fileName") String fileName) {
		System.out.println("I am called"+ fileName);
		System.out.println("classpath");
	    return resourceLoader.getResource("classpath:profilePicturesDB/" + fileName);
	}
	
   
}
