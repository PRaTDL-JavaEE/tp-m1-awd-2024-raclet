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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BandAlbumService bandAlbumService;

    private Band band, newBand;

    @BeforeEach
    public void setup() {
        band = new Band("Tame Impala", true);
        Album album = new Album("Lonerism", Genre.POP ,2012);
        band.addAlbum(album);
        album = bandAlbumService.save(album);
        band = album.getBand();

        newBand = new Band("The Beatles", false);
        newBand = bandAlbumService.save(newBand);
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
                .andExpect(content().string(containsString("<h2>Liste des Groupes</h2>")))
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
                .andExpect(content().string(containsString(band.getName())))
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

    @Test
    public void testCreateBand() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/new"
        // then: la requête est acceptée (status OK) et la vue "bandForm" est rendue
        mockMvc.perform(get("/band/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bandForm"))
                .andDo(print());
    }

    @Test
    public void testCreateBandValide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type POST vers "/band/"
        // when: la requête comporte un objet Band valide
        // then: le status de la reqûete est FOUND
        // then: une redirection a lieu vers l'adresse /band/{id} où id est l'id du groupe créé
        mockMvc.perform(post("/band")
                        .param("name", newBand.getName())
                        .param("active", Boolean.toString(newBand.getActive())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("/band/*"))
                .andDo(print());
    }

    @Test
    public void testCreateBandNomInvalide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type POST vers "/band/"
        // when: la requête comporte un objet Band invalide
        // then: la requête est acceptée (status OK)
        // then: la vue "bandForm" est rendue avec un message d'erreur de validation
        mockMvc.perform(post("/band")
                        .param("name", "")
                        .param("active", Boolean.toString(newBand.getActive())))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bandForm"))
                .andExpect(content().string(either(containsString("must not be empty"))
                        .or(containsString("must not be blank"))
                        .or(containsString("ne doit pas être vide"))))
                .andDo(print());
    }

    @Test
    public void testEditBandIdValide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/edit/{id}" avec un id valide
        // then: la requête est acceptée (status OK)
        // then: la vue "bandForm" comporte les infos de lu groupe dont l'id est id
        Long savId = band.getId();
        mockMvc.perform(get("/band/edit/" + savId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bandForm"))
                .andExpect(content().string(Matchers.containsString(band.getName())))
                .andExpect(model().attribute("band", hasProperty("id", is(savId))))
                .andDo(print());
    }

    @Test
    public void testEditBandIdInvalide() throws Exception{
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/edit/{id}" avec un id invalide
        // then: la requête est acceptée (status OK)
        // then: la vue "error" est rendue
        mockMvc.perform(get("/band/edit/" + Integer.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andDo(print());
    }

    @Test
    public void testDeleteBand() throws Exception {
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/delete" avec un id valide
        // then: la requête est acceptée (status OK)
        // then: une redirection vers la requête GET vers "/bands" a lieu
        // then: le nombre de groupe en base a diminué de 1
        long count = bandAlbumService.findAllBand().size();
        assertTrue(count > 0);
        mockMvc.perform(get("/band/delete/" + newBand.getId()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bands"))
                .andDo(print());
        assertEquals(count - 1, bandAlbumService.findAllBand().size());
    }

    @Test
    public void testDeleteBandAvecAlbum() throws Exception {
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/band/delete/{id}" où id est l'id d'un groupe en base
        //       qui est associé à au moins un album
        // then: la requête est acceptée (status OK)
        // then: la vue "error" est rendue
        long count = bandAlbumService.findAllBand().size();
        assertTrue(band.getAlbums().size() > 0);
        mockMvc.perform(get("/band/delete/" + band.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andExpect(content().string(Matchers.containsString("Le groupe est associé à un album")))
                .andDo(print());
        assertEquals(count, bandAlbumService.findAllBand().size());
    }

    @Test
    public void testFindBandActif() throws Exception {
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/bands/search?active=Y"
        // then: la requête est acceptée (status OK)
        // then: la vue "bands" est rendue
        // then : le code HTML de la vue ne comporte pas la balise  <td>non</td>
        mockMvc.perform(get("/bands/search?active=Y"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bands"))
                .andExpect(content().string(Matchers.not(Matchers.containsString("<td>non</td>"))))
                .andDo(print());
    }

    @Test
    public void testFindBandInactif() throws Exception {
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/bands/search?active=N"
        // then: la requête est acceptée (status OK)
        // then: la vue "bands" est rendue
        // then : le code HTML de la vue ne comporte pas la balise  <td>oui</td>
        mockMvc.perform(get("/bands/search?active=N"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bands"))
                .andExpect(content().string(Matchers.not(Matchers.containsString("<td>oui</td>"))))
                .andDo(print());
    }

    @Test
    public void testFindBandParamFaux() throws Exception {
        // given: un objet MockMvc qui simulate des échanges MVC
        // when: on simule du requête HTTP de type GET vers "/bands/search?active=W"
        // then: la requête est acceptée (status OK)
        // then: la vue "bands" est rendue
        mockMvc.perform(get("/bands/search?active=W"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bands"))
                .andDo(print());
    }

}