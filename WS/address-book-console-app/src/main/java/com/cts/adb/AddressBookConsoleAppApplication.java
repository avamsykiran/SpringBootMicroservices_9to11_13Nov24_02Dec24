package com.cts.adb;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookConsoleAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookConsoleAppApplication.class, args);
	}

	@Bean
	Scanner scan() {
		return new Scanner(System.in);
	}
}
