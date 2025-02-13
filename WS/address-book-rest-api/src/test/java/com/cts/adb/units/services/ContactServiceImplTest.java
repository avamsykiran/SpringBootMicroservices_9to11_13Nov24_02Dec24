package com.cts.adb.units.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cts.adb.entity.Contact;
import com.cts.adb.repo.ContactRepository;
import com.cts.adb.service.ContactService;
import com.cts.adb.service.ContactServiceImpl;

@ExtendWith(SpringExtension.class)
public class ContactServiceImplTest {

	@TestConfiguration
	static class TestConfiguarationForMockedRepoIntoService {
		@Bean
		ContactServiceImpl contactServiceImpl() {
			return new ContactServiceImpl();
		}
	}
	
	@Autowired
	private ContactService contactService;
	
	@MockBean
	private ContactRepository contactRepo;
	
	@Test
	void testAddContact() {
		Contact testData = new Contact(null,"Some Full Name","1231231234","vamsy@some.co",LocalDate.of(1988, Month.JUNE, 10));
		
		when(contactRepo.save(testData)).thenReturn(new Contact(1L,"Some Full Name","1231231234","vamsy@some.co",LocalDate.of(1988, Month.JUNE, 10)));
		
		Contact actualData = contactService.addContact(testData);
		assertNotNull(actualData.getContactId());
	}
}
