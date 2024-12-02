package com.cts.springdemo.ui;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cts.springdemo.service.GreetingService;
import com.cts.springdemo.util.Counter;

@Component
public class HomeScreen {
	
	@Value("${app.title:UnTitled Application}")
	private String appTitle;
	
	@Autowired
	private Counter counter1;
	
	@Autowired
	private Counter counter2;
	
	@Autowired
	private Counter counter3;
	
	@Autowired
	@Qualifier("greetingServiceTimeBasedImpl")
	private GreetingService greetingService;
	
	@Autowired
	private Scanner scan;

	public void run() {
		System.out.println(appTitle);
		System.out.println("------------------------------------------------");
		
		System.out.println(counter1.next());
		System.out.println(counter1.next());
		System.out.println(counter2.next());
		System.out.println(counter2.next());
		System.out.println(counter3.next());
		System.out.println(counter3.next());
		
		
		System.out.println("What's your name?");
		String unm = scan.nextLine();
		System.out.println(greetingService.greetUser(unm));
	}
}
