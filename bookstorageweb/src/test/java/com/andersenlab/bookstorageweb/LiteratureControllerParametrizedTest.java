package com.andersenlab.bookstorageweb;

import com.andersenlab.bookstorageweb.repository.LiteratureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc()
public class LiteratureControllerParametrizedTest {

    private static final List<String> LITERATURE_JSON_VALID = List.of(
        "{\"type\":\"book\", \"title\":\"Book title\", \"author\":\"author\"}",
            "{\"type\":\"magazine\", \"title\":\"Magazine title\", \"serialNumber\":20}"
    );
    private static final List<String> LITERATURE_JSON_INVALID = List.of(
            "{\"type\":\"magazine\", \"id\":1, \"title\":\"Magazine title\", \"serialNumber\":20}"
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LiteratureRepository literatureRepository;

    @BeforeEach()
    public void initMockedRepository() {
        given(
                literatureRepository.save(Mockito.any())
        ).willAnswer(i -> i.getArgument(0));
    }

    @WithMockUser(username = "username", roles = {"USER", "REDACTOR"})
    @ParameterizedTest
    @MethodSource("provideLiteratureForCreateLiterature")
    public void testCreateLiteratureBadRequest(String literatureJson, boolean isBadRequest) throws Exception {
        mockMvc.perform(post("/literature").contentType(MediaType.APPLICATION_JSON).content(literatureJson))
                .andExpect(isBadRequest ? status().isBadRequest() : status().isCreated());
    }

    private static Stream<Arguments> provideLiteratureForCreateLiterature() {
        return Stream.of(
                Arguments.of(LITERATURE_JSON_VALID.get(0), false),
                Arguments.of(LITERATURE_JSON_VALID.get(1), false),
                Arguments.of(LITERATURE_JSON_INVALID.get(0), true)
        );
    }

}
