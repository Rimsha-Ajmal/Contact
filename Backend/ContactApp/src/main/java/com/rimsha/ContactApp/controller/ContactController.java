package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.dto.ContactDto;
import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/{id}")
    public List<Contact> getContactsByUserId(@PathVariable String id) {
        return contactService.getContactsByUserId(id);
    }

    @PostMapping
    public Contact createContact(@RequestBody ContactDto contactDto) {
        return contactService.createContact(contactDto);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@RequestBody ContactDto contactDto, @PathVariable String id) {
        return contactService.updateContact(contactDto, id);
    }

    @DeleteMapping("/{id}")
    public Contact deleteContact(@PathVariable String id) {
        return contactService.deleteContact(id);
    }
}
