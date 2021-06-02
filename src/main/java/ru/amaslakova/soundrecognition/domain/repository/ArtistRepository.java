package ru.amaslakova.soundrecognition.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amaslakova.soundrecognition.domain.model.Artist;

/**
 * Repo for Artist.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
