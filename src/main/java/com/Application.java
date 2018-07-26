package com;

import com.config.ZzConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		System.out.println(1);
		SpringApplication.run(Application.class, args);
	}

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ZzConfig zzConfig;

	@RequestMapping("/z")
	public Map test(){
		logger.debug("debug");
		logger.info("info");
		logger.error("error");
		Map result = new HashMap();
		result.put("name",zzConfig.getName());
		result.put("age", zzConfig.getAge());
		return result;
	}
}
