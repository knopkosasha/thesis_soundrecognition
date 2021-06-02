package ru.amaslakova.soundrecognition.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.model.SongHash;

import java.util.List;

/**
 * Repo for SongHash entity.
 */
@Repository
public interface SongHashRepository extends JpaRepository<SongHash, Long> {

    List<Long> findAllBySong(Song song);
}
