package com.cts.adb.service;

import java.time.LocalDate;
import java.util.List;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.ContactNotFoundException;

public interface ContactService {
	
	Contact addContact(Contact c);
	Contact replaceContact(Contact c) throws ContactNotFoundException;
	void deleteContact(Long id) throws ContactNotFoundException;
	Contact getContact(Long id) throws ContactNotFoundException;
	Contact getContact(String mobile) throws ContactNotFoundException;
	List<Contact> getAll();
	List<Contact> getAll(LocalDate start,LocalDate end);
	
}
