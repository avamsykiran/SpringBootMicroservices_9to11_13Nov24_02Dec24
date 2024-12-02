package com.cts.adb.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.adb.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    
    boolean existsByMobile(String mobile);
    Optional<Contact> findByMobile(String mobile);
    Optional<Contact> findByEmailId(String emailId);
    List<Contact> findAllByFullName(String fullName);

    @Query("SELECT c FROM Contact c WHERE c.dateOfBirth BETWEEN :start AND :end")
    List<Contact> getAllBornInRange(LocalDate start,LocalDate end);
}