package doremi.controllers;

import doremi.domain.Band;
import doremi.services.BandAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BandController {

    @Autowired
    private BandAlbumService bandAlbumService;

    @GetMapping("/bands")
    public String list(Model model) {
        model.addAttribute("bands", bandAlbumService.findAllBand());
        return "bands";
    }

    @GetMapping("band/{id}")
    public String showBand(@PathVariable Long id, Model model){
        Band band = bandAlbumService.findBandById(id);
        if (band == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        model.addAttribute("band", band);
        return "bandShow";
    }

}