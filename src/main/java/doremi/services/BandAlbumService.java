package doremi.services;

import doremi.domain.Album;
import doremi.domain.Band;
import doremi.repositories.BandAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandAlbumService {

    @Autowired
    BandAlbumRepository bandAlbumRepository;

    public Album save(Album album) {
        return bandAlbumRepository.save(album);
    }

    public Band save(Band band) {
        return bandAlbumRepository.save(band);
    }

    public Album findAlbumById(Long id) {
        return bandAlbumRepository.findAlbumById(id);
    }

    public Band findBandById(Long id) {
        return bandAlbumRepository.findBandById(id);
    }

    public List<Band> findAllBand() {
        return bandAlbumRepository.findAllBand();
    }

    public BandAlbumRepository getBandAlbumRepository() {
        return bandAlbumRepository;
    }

    public void setBandAlbumRepository(BandAlbumRepository bandAlbumRepository) {
        this.bandAlbumRepository = bandAlbumRepository;
    }
}
