package com;

import com.config.ZzConfig;
import com.interceptor.TestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application extends WebMvcConfigurerAdapter {

	@Autowired
	private TestInterceptor testInterceptor;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZzConfig zzConfig;

	public Application() {
	}

	public static void main(String[] args) {
		System.out.println(1);
		SpringApplication.run(Application.class, args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.testInterceptor).addPathPatterns(new String[]{"/**"});
	}

	@RequestMapping({"/z"})
	public Map test() {
		this.logger.debug("debug");
		this.logger.info("info");
		this.logger.error("error");
		Map result = new HashMap();
		result.put("name", this.zzConfig.getName());
		result.put("age", this.zzConfig.getAge());
		return result;
	}
}
