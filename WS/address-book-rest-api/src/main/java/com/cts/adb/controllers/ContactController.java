package com.cts.adb.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.adb.entity.Contact;
import com.cts.adb.exception.ContactNotFoundException;
import com.cts.adb.exception.InvalidContactDetailsException;
import com.cts.adb.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping
	public ResponseEntity<List<Contact>> getAllAction() {
		return new ResponseEntity<List<Contact>>(contactService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{cid:[1-9][0-9]{0,3}}")
	public ResponseEntity<Contact> getByIdAction(@PathVariable("cid") long contactId) throws ContactNotFoundException {
		Contact contact = contactService.getContact(contactId);
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	
	@GetMapping("/{mno:[1-9][0-9]{9}}")
	public ResponseEntity<Contact> getByMobileAction(@PathVariable("mno") String mobile) throws ContactNotFoundException {
		Contact contact = contactService.getContact(mobile);
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	
	@GetMapping("/{start}/{end}")
	public ResponseEntity<List<Contact>> getAllInRangeAction(
			@PathVariable("start") @DateTimeFormat(iso=ISO.DATE) LocalDate start,
					@PathVariable("end") @DateTimeFormat(iso=ISO.DATE) LocalDate end) throws ContactNotFoundException {
		return new ResponseEntity<>(contactService.getAll(start, end), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Contact> addContact(@RequestBody @Valid Contact contact,BindingResult results) throws InvalidContactDetailsException{
		if(results.hasErrors()) {
			String errMsgs = results.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce((m1,m2) -> m1+","+m2).orElse(null);
			throw new InvalidContactDetailsException(errMsgs);
		}
		contact = contactService.addContact(contact);
		return new ResponseEntity<Contact>(contact, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Contact> updateContact(@RequestBody @Valid Contact contact,BindingResult results) throws InvalidContactDetailsException, ContactNotFoundException{
		if(results.hasErrors()) {
			String errMsgs = results.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce((m1,m2) -> m1+","+m2).orElse(null);
			throw new InvalidContactDetailsException(errMsgs);
		}
		contact = contactService.replaceContact(contact);
		return new ResponseEntity<Contact>(contact, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{cid}")
	public ResponseEntity<Void> deleteByIdAction(@PathVariable("cid") long contactId) throws ContactNotFoundException {
		contactService.deleteContact(contactId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
