package com.cts.springbootdemo.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceSimpleImpl implements GreetingService {

	@Override
	public String greetUser(String userName) {
		return "Hello "+userName;
	}

}
