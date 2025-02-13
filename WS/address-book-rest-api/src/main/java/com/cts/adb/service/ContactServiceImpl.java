package com.cts.adb.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.ContactNotFoundException;
import com.cts.adb.repo.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepo;
	
	@Override
	public Contact addContact(Contact c)  {
		return contactRepo.save(c);
	}

	@Override
	public Contact replaceContact(Contact c) throws ContactNotFoundException  {
		if(!contactRepo.existsById(c.getContactId())) {
			throw new ContactNotFoundException("No Such Contact Found");
		}
		return contactRepo.save(c);
	}

	@Override
	public void deleteContact(Long id) throws ContactNotFoundException  {
		if(!contactRepo.existsById(id)) {
			throw new ContactNotFoundException("No Such Contact Found");
		}
		contactRepo.deleteById(id);
	}

	@Override
	public Contact getContact(Long id) throws ContactNotFoundException  {
		if(!contactRepo.existsById(id)) {
			throw new ContactNotFoundException("No Such Contact Found");
		}
		return contactRepo.findById(id).orElse(null);
	}

	@Override
	public List<Contact> getAll() {
		return contactRepo.findAll();
	}

	@Override
	public Contact getContact(String mobile) throws ContactNotFoundException {
		if(!contactRepo.existsByMobile(mobile)) {
			throw new ContactNotFoundException("No Such Contact Found For The Given Mobile Number");
		}
		return contactRepo.findByMobile(mobile).orElse(null);
	}

	@Override
	public List<Contact> getAll(LocalDate start, LocalDate end) {
		return contactRepo.getAllBornInRange(start, end);
	}

}
