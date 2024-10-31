package com.rimsha.ContactApp.service;

import com.rimsha.ContactApp.dto.ContactDto;
import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.repo.ContactRepo;
import com.rimsha.ContactApp.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Contact> getContactsByUserId(String id) {
        return contactRepo.findAllByUser_Id(id);
    }

    public List<Contact> getContactsByContactId(String id) {
        return contactRepo.findAllById(id);
    }


    public Contact createContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());
        contact.setAddress(contactDto.getAddress());

        User user = userRepo.findById(contactDto.getUser_id()).get();
        contact.setUser(user);
        contactRepo.save(contact);
        return contact;
    }

    public Contact updateContact(ContactDto contactDto, String id) {
        Contact updateContact = contactRepo.findById(id).get();

        User user = userRepo.findById(contactDto.getUser_id()).get();
        updateContact.setUser(user);

        updateContact.setFirstName(contactDto.getFirstName());
        updateContact.setLastName(contactDto.getLastName());
        updateContact.setPhone(contactDto.getPhone());
        updateContact.setEmail(contactDto.getEmail());
        updateContact.setAddress(contactDto.getAddress());

        contactRepo.save(updateContact);
        return updateContact;
    }

    public Contact deleteContact(String id){
        Contact deleteContact = contactRepo.findById(id).orElse(null);

        if(deleteContact != null) {
            Contact contact = deleteContact;
            contact.setUser(null);
            contactRepo.save(contact);
            contactRepo.delete(contact);
            return deleteContact;
        }
        return null;
    }

}


