package contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rimsha.ContactApp.controller.ContactController;
import com.rimsha.ContactApp.dto.FilterContactDto;
import com.rimsha.ContactApp.model.Contact;
import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }

    @Test
    public void getContactsByContactId_success() throws Exception {

        String contactId = "5e7a4793-0fc6-4e4f-bded-4a4e22d474fd";

        Mockito.when(contactService.getContactsByContactId(contactId)).thenReturn(Optional.of(contact_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Abdul")))
                .andExpect(jsonPath("$.lastName", is("Moiz")))
                .andExpect(jsonPath("$.phone", is("03361132916")))
                .andExpect(jsonPath("$.email", is("moiz@gmail.com")));

    }

    @Test
    public void getContactsByUserId_success() throws Exception {

        String userId = "c8aa4a0d-b331-4cba-9728-ec4e02be3647";
        String search = "Moiz";
        String sortBy = "firstName";
        int page = 1;
        int size = 3;

        List<Contact> contactList = new ArrayList<>(Arrays.asList(contact_1, contact_2));

        Mockito.when(contactService.getContactsByUserId(eq(userId), eq(search), any(FilterContactDto.class), eq(page), eq(size))).thenReturn((contactList));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/user/{id}", userId)
                        .param("search", search)
                        .param("sortBy", sortBy)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Abdul")))
                .andExpect(jsonPath("$[1].firstName", is("Hannan")));

    }

    @Test
    public void createContact_success() throws Exception {
        Contact newContact = Contact.builder()
                .firstName("Test")
                .lastName("User")
                .email("testUser@gmail.com")
                .phone("0324-2701482")
                .address("Germany")
                .user(new User("c8aa4a0d-b331-4cba-9728-ec4e02be3647", "Rimsha", "Ajmal",
                        "rimsha03@gmail.com", "rimsha", "0336-1132916", "Gulistan e Jauhar"))
                .build();

        Mockito.when(contactService.createContact(Mockito.any(com.rimsha.ContactApp.dto.ContactDto.class))).thenReturn(newContact);

        String content = objectWriter.writeValueAsString((newContact));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Test")));
    }

    @Test
    public void updateContact_success() throws Exception {
        String userId = "1";
        Contact updatedContact = Contact.builder()
                .id(userId)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .email("updatedEmail@gmail.com")
                .phone("0324-2701482")
                .address("UpdatedAddress")
                .build();

        Mockito.when(contactService.updateContact(Mockito.any(com.rimsha.ContactApp.dto.ContactDto.class), Mockito.eq(userId))).thenReturn(updatedContact);

        String content = objectWriter.writeValueAsString(updatedContact);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/contact/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.firstName", is("UpdatedFirstName")))
                .andExpect(jsonPath("$.lastName", is("UpdatedLastName")))
                .andExpect(jsonPath("$.email", is("updatedEmail@gmail.com")))
                .andExpect(jsonPath("$.address", is("UpdatedAddress")));
    }

    @Test
    public void deleteContact_success() throws Exception {
        String contactId = "5e7a4793-0fc6-4e4f-bded-4a4e22d474fd";

        Mockito.when(contactService.deleteContact(contactId)).thenReturn(contact_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/contact/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(contactId)))
                .andExpect(jsonPath("$.firstName", is("Abdul")))
                .andExpect(jsonPath("$.lastName", is("Moiz")))
                .andExpect(jsonPath("$.email", is("moiz@gmail.com")));
    }

}
