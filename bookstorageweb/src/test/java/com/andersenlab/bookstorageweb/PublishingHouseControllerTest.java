package com.andersenlab.bookstorageweb;

import com.andersenlab.bookstorageweb.entity.PublishingHouse;
import com.andersenlab.bookstorageweb.repository.PublishingHouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class PublishingHouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublishingHouseRepository publishingHouseRepository;

    @Test
    public void testGetPublishingHouses() throws Exception {
        given(publishingHouseRepository.findAll()).willReturn(
                List.of(new PublishingHouse(1, "publishing house name"))
        );

        mockMvc.perform(
                get("/publishingHouses")
        ).andExpect(
                jsonPath("$[0].name", is("publishing house name"))
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetPublishingHouseById() throws Exception {
        given(publishingHouseRepository.findById(1L)).willReturn(
                Optional.of(new PublishingHouse(1L, "publishing house name"))
        );

        mockMvc.perform(
                get("/publishingHouses/1")
        ).andExpect(
                jsonPath("$.name", is("publishing house name"))
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetPublishingHouseByIdNotFound() throws Exception {
        given(publishingHouseRepository.findById(1L)).willReturn(
                Optional.empty()
        );

        mockMvc.perform(
                get("/publishingHouses/1")
        ).andExpect(status().isNotFound());
    }

}
