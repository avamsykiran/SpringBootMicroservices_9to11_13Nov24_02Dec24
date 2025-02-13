package com.cts.adb.entity;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="contacts")
public class Contact{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;
    
    @NotNull(message = "Full Name can not be left blank")
    @NotBlank(message = "Full Name can not be left blank")
    @Size(min = 5,max=50,message = "Full Name is expected to be between 5 and 50 letter in length")
    private String fullName;
    
    @NotNull(message = "Mobile can not be left blank")
    @NotBlank(message = "Mobile can not be left blank")
    @Pattern(regexp = "[1-9][0-9]{9}",message = "Mobile is expected to be a ten digit number")
    private String mobile;
    
    @NotNull(message = "Mail Id can not be left blank")
    @NotBlank(message = "Mail Id can not be left blank")
    @Email(message = "A valid maild id expected")
    private String emailId;
    
    @NotNull(message = "Date Of Birth is a mandatory field")
    @Past(message = "Date Of Birth must be a past date")
    @DateTimeFormat(iso = ISO.DATE)
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