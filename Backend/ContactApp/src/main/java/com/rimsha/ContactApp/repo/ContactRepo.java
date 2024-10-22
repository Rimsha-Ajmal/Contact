package com.rimsha.ContactApp.repo;

import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    Optional<Contact> findById(String id);

    List<Contact> findAllByUser_Id(String userId);
}
