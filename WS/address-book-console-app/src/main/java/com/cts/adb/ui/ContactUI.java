package com.cts.adb.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.AdbException;
import com.cts.adb.service.ContactService;

@Component
public class ContactUI implements CommandLineRunner {

	@Value("${spring.application.name:UnTitled App}")
	private String title;
	
	@Autowired
	private Scanner scan;
	
	@Autowired
	private ContactService contactService;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(title);
		System.out.println("----------------------------------------");
		
		boolean quit=false;
		
		while(!quit) {
			System.out.println("Cmd (ADD/LIST/DELETE/QUIT): ");
			String cmd = scan.nextLine().toLowerCase();
			
			switch(cmd) {
			case "add": doAdd(); break;
			case "list": doList(); break;
			case "delete": doDelete(); break;
			case "quit": quit=true; break;
			default: System.out.println("Invalid Command");
			}
		}
		
		System.out.println("Application Terminated");
	}

	void doAdd() {
		Contact c = new Contact();
		
		System.out.print("Full Name: ");
		c.setFullName(scan.nextLine());
		System.out.print("Mobile: ");
		c.setMobile(scan.nextLine());
		System.out.print("Mail Id: ");
		c.setEmailId(scan.nextLine());
		System.out.print("Date Of Birth (yyyy-MM-dd): ");
		c.setDateOfBirth(LocalDate.parse(scan.nextLine()));
		
		try {
			c = contactService.addContact(c);
			System.out.println("Contact Saved wiht id "+c.getContactId()+"!");
		} catch (AdbException e) {
			System.out.println(e.getMessage());
		}
	}
	
	void doList() {
		List<Contact> contacts = contactService.getAll();
		
		if(contacts.isEmpty()) {
			System.out.println("No Records Found");
		}else {
			contacts.stream().forEach(System.out::println);
		}
	}
	
	void doDelete() {
		System.out.print("Contact Id: ");
		long id = scan.nextLong();
		try {
			contactService.deleteContact(id);
			System.out.println("Contact Deleted!");
		} catch (AdbException e) {
			System.out.println(e.getMessage());
		}
	}
}
