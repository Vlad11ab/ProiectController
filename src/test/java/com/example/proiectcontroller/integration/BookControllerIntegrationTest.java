package com.example.proiectcontroller.integration;

import com.example.proiectcontroller.book.dtos.BookCreateRequest;
import com.example.proiectcontroller.book.dtos.BookPatchRequest;
import com.example.proiectcontroller.book.dtos.BookPutRequest;
import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanDatabase(){
        bookRepository.deleteAll();
    }

    @Test
    void createGetUpdatePatchDeleteFlow() throws Exception{
        BookCreateRequest createRequest = new BookCreateRequest(
                "Book",
                "Education"
        );

        MvcResult createResult = mockMvc.perform(post("/api/v1/books/add")
                .with(jwt().authorities(() -> "book:write"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        BookResponse created = objectMapper.readValue(createResult.getResponse().getContentAsByteArray(), BookResponse.class);

        mockMvc.perform(get("/api/v1/books/{bookId}", created.id())
                .with(jwt().authorities(() -> "book:read")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book"));

        BookPutRequest put = new BookPutRequest("UpdatedBook","UpdatedEducation");
        mockMvc.perform(put("/api/v1/books/update/{bookId}",created.id())
                .with(jwt().authorities(() -> "book:edit"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(put)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("UpdatedBook"));

        BookPatchRequest patch = new BookPatchRequest(null,"PatchedEducation");
        mockMvc.perform(patch("/api/v1/books/edit/{bookId}",created.id())
                .with(jwt().authorities(() -> "book:edit"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.category").value("PatchedEducation"));

        mockMvc.perform(delete("/api/v1/books/delete/{bookId}", created.id())
                .with(jwt().authorities(() -> "book:delete")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/books/{bookId}",created.id())
                .with(jwt().authorities(() -> "book:read")))
                .andExpect(status().isNotFound());

    }

    @Test
    void duplicateCreateReturnsConflict() throws Exception {
        BookCreateRequest request = new BookCreateRequest("Book2", "Education2");

     mockMvc.perform(post("/api/v1/books/add")
                        .with(jwt().authorities(() -> "book:write"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
             .andDo(print())
                     .andExpect(status().isCreated()).andReturn();


     mockMvc.perform(post("/api/v1/books/add")
                        .with(jwt().authorities(() -> "book:write"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void validationErrorAreReturned() throws Exception{
        BookCreateRequest invalid = new BookCreateRequest(null,null);

        mockMvc.perform(post("/api/v1/books/add")
                .with(jwt().authorities(() -> "book:write"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.path").value("/api/v1/books/add"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void unauthenticatedRequestReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("Trebuie sa fii autentificat pentru a accesa aceasta resursa."))
                .andExpect(jsonPath("$.path").value("/api/v1/books/1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void authenticatedWithoutPermissionReturnsForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/books/delete/1")
                .with(jwt().authorities(() -> "book:read")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.error").value("FORBIDDEN"))
                .andExpect(jsonPath("$.message").value("Nu ai permisiunea necesara pentru aceasta actiune."))
                .andExpect(jsonPath("$.path").value("/api/v1/books/delete/1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


}
