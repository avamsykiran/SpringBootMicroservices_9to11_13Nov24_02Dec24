package com.cts.adb.service;

import java.util.List;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.AdbException;

public interface ContactService {
	
	Contact addContact(Contact c) throws AdbException;
	Contact replaceContact(Contact c) throws AdbException;
	void deleteContact(Long id) throws AdbException;
	Contact getContact(Long id) throws AdbException;
	List<Contact> getAll();
	
}
