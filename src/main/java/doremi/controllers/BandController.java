package doremi.controllers;

import doremi.domain.Band;
import doremi.services.BandAlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/band/new")
    public String createBand(Model model){
        model.addAttribute("band", new Band());
        return "bandForm";
    }

    @PostMapping(value = "/band")
    public String createBand(@Valid Band band,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "bandForm";
        }
        band = bandAlbumService.save(band);
        return "redirect:/band/" + band.getId();
    }

}