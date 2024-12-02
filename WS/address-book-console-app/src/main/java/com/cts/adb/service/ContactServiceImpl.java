package com.cts.adb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.AdbException;
import com.cts.adb.repo.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepo;
	
	@Override
	public Contact addContact(Contact c) throws AdbException {
		return contactRepo.save(c);
	}

	@Override
	public Contact replaceContact(Contact c) throws AdbException {
		if(!contactRepo.existsById(c.getContactId())) {
			throw new AdbException("No Such Contact Found");
		}
		return contactRepo.save(c);
	}

	@Override
	public void deleteContact(Long id) throws AdbException {
		if(!contactRepo.existsById(id)) {
			throw new AdbException("No Such Contact Found");
		}
		contactRepo.deleteById(id);
	}

	@Override
	public Contact getContact(Long id) throws AdbException {
		if(!contactRepo.existsById(id)) {
			throw new AdbException("No Such Contact Found");
		}
		return contactRepo.findById(id).orElse(null);
	}

	@Override
	public List<Contact> getAll() {
		return contactRepo.findAll();
	}

}
