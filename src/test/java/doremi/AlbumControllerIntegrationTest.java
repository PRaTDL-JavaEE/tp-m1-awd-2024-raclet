package doremi;

import doremi.domain.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private DataLoader dataLoader;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    private String jsonResult;


    @Test
    public void testFindAlbumsByYear2001() throws Exception {

        // given: une album en base
        Album a = dataLoader.getAlbum1();
        assertThat(a.getYear(), is(2001));

        // when: l'utilisateur émet une requête pour obtenir la liste des albums de 2001
        mockMvc.perform(get("/albums?year=2001"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est au format JSON et utf8
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        // then: le résultat obtenu contient le titre de l'album
        assertThat(jsonResult, containsString(a.getTitle()));
        // then: le résultat obtenu contient l'année de l'album
        assertThat(jsonResult, containsString(Integer.toString(a.getYear())));

    }

}
