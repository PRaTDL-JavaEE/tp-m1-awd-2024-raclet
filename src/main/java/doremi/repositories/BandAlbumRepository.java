package doremi.repositories;

import doremi.domain.Album;
import doremi.domain.Band;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BandAlbumRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Album album) {
        entityManager.persist(album);
    }

    public void save(Band band) {
        entityManager.persist(band);
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
