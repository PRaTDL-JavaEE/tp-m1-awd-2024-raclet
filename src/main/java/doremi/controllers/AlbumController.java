package doremi.controllers;

import doremi.domain.Album;
import doremi.services.BandAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {

    @Autowired
    private BandAlbumService bandAlbumService;

    @GetMapping(value = "/albums", produces="application/json;charset=UTF-8")
    public List<Album> findAlbumsByYear(@RequestParam(value = "year") Integer year) {
        return bandAlbumService.findAlbumsByYear(year);
    }

}
