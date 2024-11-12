package contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rimsha.ContactApp.dto.ContactDto;
import com.rimsha.ContactApp.dto.FilterContactDto;
import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.repo.ContactRepo;
import com.rimsha.ContactApp.repo.UserRepo;
import com.rimsha.ContactApp.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ContactRepo contactRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private ContactService contactService;

    Contact contact_1 = new Contact("5e7a4793-0fc6-4e4f-bded-4a4e22d474fd", "Abdul", "Moiz",
            "03361132916", "Block-17, B-102, Naveed Cottages, Gulistan-e-Johar", "moiz@gmail.com",
            new User("c8aa4a0d-b331-4cba-9728-ec4e02be3647", "Rimsha", "Ajmal",
                    "rimsha03@gmail.com", "rimsha", "0336-1132916", "Gulistan e Jauhar"));

    Contact contact_2 = new Contact("acd43bfb-29f4-4c07-a6d0-a89bc20e9854", "Hannan", "Rai",
            "03361132916", "Block-17, B-102, Naveed Cottages, Gulistan-e-Johar", "hannan123@gmail.com",
            new User("c8aa4a0d-b331-4cba-9728-ec4e02be3647", "Rimsha", "Ajmal",
                    "rimsha03@gmail.com", "rimsha", "0336-1132916", "Gulistan e Jauhar"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contactService).build();
    }

    @Test
    public void getContactsByContactId_success() throws Exception{

        String contactId = "acd43bfb-29f4-4c07-a6d0-a89bc20e9854";

        when(contactRepo.findAllById(contactId)).thenReturn(Optional.ofNullable(contact_2));

        Optional<Contact> result = contactService.getContactsByContactId((contactId));

        assertTrue(result.isPresent());
        assertEquals("Hannan", result.get().getFirstName());
        assertEquals("Rai", result.get().getLastName());

    }

    @Test
    public void getContactsByUserId_withoutSearch() throws Exception{

        List<Contact> contactList = new ArrayList<>(Arrays.asList(contact_1, contact_2));

        String userId = "c8aa4a0d-b331-4cba-9728-ec4e02be3647";

        FilterContactDto filterContactDto = new FilterContactDto();
        filterContactDto.setSortBy("A-Z");

        when(contactRepo.findAllByUser_Id(eq(userId), any(Pageable.class))).thenReturn(contactList);

        List<Contact> result = contactService.getContactsByUserId(userId, null , filterContactDto, 1, 3);

        assertEquals(2, result.size());
        assertEquals("Hannan", result.get(1).getFirstName());
    }

    @Test
    public void getContactsByUserId_withSearch() throws Exception{

        List<Contact> contactList = new ArrayList<>(Arrays.asList(contact_1, contact_2));

        String userId = "c8aa4a0d-b331-4cba-9728-ec4e02be3647";
        String search = "Hannan";

        FilterContactDto filterContactDto = new FilterContactDto();
        filterContactDto.setSortBy("A-Z");

        when(contactRepo.findAllByUserIdAndSearch(eq(userId), eq(search), any(Pageable.class))).thenReturn(contactList);

        List<Contact> result = contactService.getContactsByUserId(userId, search, filterContactDto, 1, 3);

        assertEquals(2, result.size());
        assertEquals("Hannan", result.get(1).getFirstName());
    }

    @Test
    public void createContact_success() throws Exception {

        ContactDto contactDto = new ContactDto();
        contactDto.setFirstName("Test");
        contactDto.setLastName("User");
        contactDto.setEmail("testUser@gmail.com");
        contactDto.setPhone("0324-2701482");
        contactDto.setAddress("London");
        contactDto.setUser_id("c8aa4a0d-b331-4cba-9728-ec4e02be3647");

        User user = new User();
        user.setId("c8aa4a0d-b331-4cba-9728-ec4e02be3647");

        Contact savedContact = Contact.builder()
                .firstName("Test")
                .lastName("User")
                .email("testUser@gmail.com")
                .phone("0324-2701482")
                .address("London")
                .user(user)
                .build();

        Mockito.when(userRepo.findById("c8aa4a0d-b331-4cba-9728-ec4e02be3647")).thenReturn(Optional.of(user));
        Mockito.when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(savedContact);

        Contact result = contactService.createContact(contactDto);

        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("testUser@gmail.com", result.getEmail());
        assertEquals(user, result.getUser());

        verify(userRepo).findById("c8aa4a0d-b331-4cba-9728-ec4e02be3647");
        verify(contactRepo).save(Mockito.any(Contact.class));
    }

    @Test
    public void updateContact_success() {
        String contactId = "contact123";
        ContactDto contactDto = new ContactDto();
        contactDto.setFirstName("Jane");
        contactDto.setLastName("Doe");
        contactDto.setEmail("janedoe@example.com");
        contactDto.setPhone("987-654-3210");
        contactDto.setAddress("456 Another St");
        contactDto.setUser_id("user123");

        User mockUser = new User();
        mockUser.setId("user123");

        Contact existingContact = new Contact();
        existingContact.setId(contactId);
        existingContact.setFirstName("John");
        existingContact.setLastName("Smith");
        existingContact.setEmail("johnsmith@example.com");
        existingContact.setPhone("123-456-7890");
        existingContact.setAddress("123 Main St");

        Mockito.when(contactRepo.findById(contactId)).thenReturn(Optional.of(existingContact));
        Mockito.when(userRepo.findById("user123")).thenReturn(Optional.of(mockUser));
        Mockito.when(contactRepo.save(any(Contact.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Contact updatedContact = contactService.updateContact(contactDto, contactId);

        assertEquals("Jane", updatedContact.getFirstName());
        assertEquals("Doe", updatedContact.getLastName());
        assertEquals("janedoe@example.com", updatedContact.getEmail());
        assertEquals("987-654-3210", updatedContact.getPhone());
        assertEquals("456 Another St", updatedContact.getAddress());
        assertEquals(mockUser, updatedContact.getUser());

        verify(contactRepo).findById(contactId);
        verify(userRepo).findById("user123");
        verify(contactRepo).save(existingContact);
    }

    @Test
    public void deleteContact_ContactExists() {
        String contactId = "contact123";
        Contact existingContact = new Contact();
        existingContact.setId(contactId);
        existingContact.setFirstName("John");
        existingContact.setLastName("Doe");

        Mockito.when(contactRepo.findById(contactId)).thenReturn(Optional.of(existingContact));
        Mockito.when(contactRepo.save(any(Contact.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Contact deletedContact = contactService.deleteContact(contactId);


        assertEquals(existingContact, deletedContact);
        assertNull(deletedContact.getUser());  // Verify user is set to null
        verify(contactRepo).findById(contactId);
        verify(contactRepo).save(existingContact);  // Saved with user set to null
        verify(contactRepo).delete(existingContact);  // Deleted from repo
    }

    @Test
    public void deleteContact_ContactDoesNotExist() {
        String contactId = "contact123";
        Mockito.when(contactRepo.findById(contactId)).thenReturn(Optional.empty());

        Contact deletedContact = contactService.deleteContact(contactId);

        assertNull(deletedContact);  // No contact should be returned
        verify(contactRepo).findById(contactId);
        verify(contactRepo, never()).save(any(Contact.class));  // save should not be called
        verify(contactRepo, never()).delete(any(Contact.class));  // delete should not be called
    }

}
