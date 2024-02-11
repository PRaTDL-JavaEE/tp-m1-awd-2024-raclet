package doremi;

import doremi.domain.Album;
import doremi.domain.Band;
import doremi.domain.Genre;
import doremi.services.BandAlbumService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BandAlbumService bandAlbumService;

    private Band band;

    @BeforeEach
    public void setup() {
        band = new Band("Tame Impala", true);
        Album album = new Album("Lonerism", Genre.POP ,2012);
        band.addAlbum(album);
        album = bandAlbumService.save(album);
        band = album.getBand();
    }

    @Test
    public void testGetBands() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/bands"
        // then: la requête est acceptée (status OK) et la vue "bands" est rendue
        // then: la vue retournée comporte le code HTML "<h2>Liste des groupes</h2>"
        mockMvc.perform(get("/bands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bands"))
                .andExpect(content().string(Matchers.containsString("<h2>Liste des Groupes</h2>")))
                .andDo(print());
    }

    @Test
    public void testReadBandIdValide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/{id}" avec un id valide
        // then: la requête est acceptée (status OK) et la vue "bandShow" est rendue
        // then: la vue retournée comporte les infos sur le groupe correspondant à l'id de l'URL
        mockMvc.perform(get("/band/" + band.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bandShow"))
                .andExpect(content().string(Matchers.containsString(band.getName())))
                .andDo(print());
    }

    @Test
    public void testReadBandIdInvalide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/{id}" avec un id invalide
        // then: la requête est acceptée (status OK) et la vue "error" est rendue
        mockMvc.perform(get("/band/" + Integer.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andDo(print());
    }

}
