package ru.amaslakova.soundrecognition.domain.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;

import java.util.List;
import java.util.Set;

/**
 * Repo for Song entity.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {

	List<Song> findAllByArtist(Artist artist);

	List<Song> findAllByAlbum(Album album);

	List<Song> findAllByGenre(Genre genre);

	List<Song> findAllByIdIn(Set<Long> ids);
}
