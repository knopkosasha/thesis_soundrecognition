package ru.amaslakova.soundrecognition.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;

import java.util.List;
import java.util.Set;

/**
 * Repo for Album entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	List<Album> findAllByIdIn(Set<Long> ids);

	List<Album> findAllByArtist(Artist artist);
}
