package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.dto.ContactDto;
import com.rimsha.ContactApp.dto.FilterContactDto;
import com.rimsha.ContactApp.dto.PaginationDto;
import com.rimsha.ContactApp.exceptionHandling.ResourceNotFoundException;
import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")

public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/user/{id}")
    public PaginationDto getContactsByUserId(@PathVariable String id, @RequestParam(required = false) String search, @RequestParam(required = false) String sortBy, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        if (page <= 0 || size <= 0) {
            throw new ResourceNotFoundException("Page and size parameters must be greater than 0.");
        }

        FilterContactDto filterContactDto = new FilterContactDto();
        filterContactDto.setSortBy(sortBy);

        PaginationDto paginationDto = contactService.getContactsByUserId(id, search, filterContactDto, page, size);

        if (paginationDto == null || paginationDto.getContact().isEmpty()) {
            throw new ResourceNotFoundException("No contacts found for user with ID: " + id);
        }

        return paginationDto;
    }

    @PostMapping
    public Contact createContact(@RequestBody ContactDto contactDto) {
        if (contactDto == null || contactDto.getFirstName() == null || contactDto.getLastName() == null || contactDto.getEmail() == null || contactDto.getPhone() == null || contactDto.getAddress() == null) {
            throw new ResourceNotFoundException("Contact creation failed. Ensure none of the field is empty.");
        }
        else if (contactDto.getFirstName().isEmpty() || contactDto.getLastName().isEmpty() || contactDto.getEmail().isEmpty() || contactDto.getPhone().isEmpty() || contactDto.getAddress().isEmpty() || contactDto.getUser_id().isEmpty()) {
            throw new ResourceNotFoundException("Contact creation failed. Ensure none of the field is empty.");
        }
        return contactService.createContact(contactDto);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@RequestBody ContactDto contactDto, @PathVariable String id) {
        if (!contactService.getContactsByContactId(id).isPresent()) {
            throw new ResourceNotFoundException("Contact with ID: " + id + " not found. Update operation failed.");
        }
        else if (contactDto.getFirstName().isEmpty() || contactDto.getLastName().isEmpty() || contactDto.getEmail().isEmpty() || contactDto.getPhone().isEmpty() || contactDto.getAddress().isEmpty() || contactDto.getUser_id().isEmpty()) {
            throw new ResourceNotFoundException("Contact creation failed. Ensure none of the field is empty.");
        }

        return contactService.updateContact(contactDto, id);
    }

    @DeleteMapping("/{id}")
    public Contact deleteContact(@PathVariable String id) {

        Optional<Contact> contact = contactService.getContactsByContactId(id);
        if (!contact.isPresent()) {
            throw new ResourceNotFoundException("Contact with ID: " + id + " does not exist. Delete operation failed.");
        }
        return contactService.deleteContact(id);
    }

    @GetMapping("/{id}")
    public Optional<Contact> getContactsByContactId(@PathVariable String id) {
        return Optional.ofNullable(contactService.getContactsByContactId(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id)));
    }
}
