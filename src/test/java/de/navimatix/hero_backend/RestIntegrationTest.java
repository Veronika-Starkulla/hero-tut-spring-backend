package de.navimatix.hero_backend;

import de.navimatix.hero_backend.entity.Hero;
import de.navimatix.hero_backend.repository.HeroRepo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RestIntegrationTest {

    @Autowired
    HeroRepo repo;

    MockMvc mvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("https")
                        .withHost("reporting.api.url")
                        .withPort(433))
                .alwaysDo(
                        document("{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                )
                .build();
    }

    @Test
    @SneakyThrows
    void getHero() {
        Hero hero = new Hero();
        hero.setId(1);
        hero.setName("Superhero");
        repo.save(hero);

        ResultActions resultActions = mvc.perform(get("/hero/{id}", 1));
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("id").value("1"));
        resultActions.andExpect(jsonPath("name").value("Superhero"));
        resultActions.andDo(document("getHero",
                responseFields(fieldWithPath("id").description("UUID of Hero"), fieldWithPath("name").description("Hero's Name"))));
    }

}
