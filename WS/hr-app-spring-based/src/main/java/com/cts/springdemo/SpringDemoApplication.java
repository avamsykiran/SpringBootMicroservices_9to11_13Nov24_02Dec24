package com.cts.springdemo;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.cts.springdemo.ui.HomeScreen;

@Configuration
@ComponentScan("com.cts.springdemo")
@PropertySource("classpath:props.properties")
public class SpringDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringDemoApplication.class);
		HomeScreen hs = (HomeScreen) context.getBean("homeScreen");
		hs.run();
	}

	@Bean
	public Scanner kbin() {
		return new Scanner(System.in);
	}
}
