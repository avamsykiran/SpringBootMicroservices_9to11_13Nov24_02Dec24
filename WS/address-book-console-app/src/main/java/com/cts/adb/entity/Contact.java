package com.cts.adb.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contacts")
public class Contact{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;
    private String fullName;
    private String mobile;
    private String emailId;
    private LocalDate dateOfBirth;
    
    public Contact() {
    	super();
    }
    
    public Contact(Long contactId, String fullName, String mobile, String emailId, LocalDate dateOfBirth) {
		super();
		this.contactId = contactId;
		this.fullName = fullName;
		this.mobile = mobile;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contactId, dateOfBirth, emailId, fullName, mobile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(contactId, other.contactId) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(emailId, other.emailId) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(mobile, other.mobile);
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", fullName=" + fullName + ", mobile=" + mobile + ", emailId="
				+ emailId + ", dateOfBirth=" + dateOfBirth + "]";
	}    
}