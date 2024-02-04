package doremi.services;

import doremi.domain.Album;
import doremi.domain.Band;
import doremi.repositories.BandAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BandAlbumService {

    @Autowired
    BandAlbumRepository bandAlbumRepository;

    public void save(Album album) {
        bandAlbumRepository.save(album);
    }

    public void save(Band band) {
        bandAlbumRepository.save(band);
    }

    public Album findAlbumById(Long id) {
        return bandAlbumRepository.findAlbumById(id);
    }

    public Band findBandById(Long id) {
        return bandAlbumRepository.findBandById(id);
    }

    public BandAlbumRepository getBandAlbumRepository() {
        return bandAlbumRepository;
    }

    public void setBandAlbumRepository(BandAlbumRepository bandAlbumRepository) {
        this.bandAlbumRepository = bandAlbumRepository;
    }
}
