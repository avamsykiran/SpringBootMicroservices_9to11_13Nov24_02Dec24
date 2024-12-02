package com.cts.adb.units.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cts.adb.entity.Contact;
import com.cts.adb.repo.ContactRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ContactRepositoryTest {

	@Autowired
	private ContactRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	void whenContactInserted_thenContactMustBeRetrivable() {
		
		Contact expectedData = new Contact(null,"Some Full Name","1231231234","vamsy@some.co",LocalDate.of(1988, Month.JUNE, 10));
		expectedData = repo.save(expectedData);
		
		Long pk = expectedData.getContactId();
		
		Contact actualData = entityManager.find(Contact.class, pk);
		
		assertEquals(expectedData, actualData);		
	}
}
