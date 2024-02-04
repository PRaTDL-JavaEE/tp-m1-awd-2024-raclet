package doremi.repositories;

import doremi.domain.Album;
import doremi.domain.Band;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BandAlbumRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Album save(Album album) {
        Band savedBand = this.save(album.getBand());
        album.setBand(savedBand);
        Album savedAlbum = entityManager.merge(album);
        savedBand.addAlbum(savedAlbum);

        return savedAlbum;
    }

    @Transactional
    public Band save(Band band) {
        return entityManager.merge(band);
    }

    public Album findAlbumById(Long id) {
        return entityManager.find(Album.class, id);
    }

    public Band findBandById(Long id) {
        return entityManager.find(Band.class, id);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
