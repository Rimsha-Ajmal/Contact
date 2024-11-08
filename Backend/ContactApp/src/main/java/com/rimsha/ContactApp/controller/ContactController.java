package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.dto.ContactDto;
import com.rimsha.ContactApp.dto.FilterContactDto;
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

    @GetMapping("/user/{id}")
    public List<Contact> getContactsByUserId(@PathVariable String id, @RequestParam(required = false) String search, @RequestParam(required = false) String sortBy, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "3") int size) {
        FilterContactDto filterContactDto = new FilterContactDto();
        filterContactDto.setSortBy(sortBy);
        return contactService.getContactsByUserId(id, search, filterContactDto, page, size);
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

    @GetMapping("/{id}")
    public Optional<Contact> getContactsByContactId(@PathVariable String id) {
        return contactService.getContactsByContactId(id);
    }
}
