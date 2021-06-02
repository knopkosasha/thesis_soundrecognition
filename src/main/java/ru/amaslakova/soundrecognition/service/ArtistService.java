package ru.amaslakova.soundrecognition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amaslakova.soundrecognition.common.util.CollectionUtil;
import ru.amaslakova.soundrecognition.domain.dto.ArtistDTO;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.repository.AlbumRepository;
import ru.amaslakova.soundrecognition.domain.repository.ArtistRepository;
import ru.amaslakova.soundrecognition.domain.repository.song.SongRepository;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistCreationException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistNotFoundException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistUpdateException;
import ru.amaslakova.soundrecognition.service.converter.ArtistConverter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for Artist.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {

	private static final Long DEFAULT_ARTIST_ID = 0L;

	private final ArtistRepository artistRepository;
	private final SongRepository songRepository;
	private final AlbumRepository albumRepository;

	private final ArtistConverter artistConverter;

	@Transactional
	public Artist createEntity(ArtistDTO dto) throws ArtistCreationException {
		if (dto.getId() != null) {
			throw new ArtistCreationException("album id exists");
		}
		Artist newArtist = new Artist();
		newArtist.setName(dto.getName());
		this.artistRepository.save(newArtist);
		log.info("New Artist was created {}", newArtist);
		return newArtist;
	}

	@Transactional
	public ArtistDTO createArtist(ArtistDTO dto) throws ArtistCreationException {
		return this.artistConverter.toDTO(this.createEntity(dto));
	}

	@Transactional
	public ArtistDTO updateArtist(ArtistDTO dto) throws ArtistNotFoundException, ArtistUpdateException {
		if (dto.getId() == null) {
			throw new ArtistUpdateException("artist id is null");
		}
		Artist artist = this.getEntityById(dto.getId());
		artist.setName(dto.getName());

		Artist defArtist = this.getDefaultArtist();

		//Albums
		Set<Long> currentArtistAlbumsIds = artist.getAlbums().stream()
			.map(Album::getId).collect(Collectors.toSet());
		//set default for exceeded
		if (!currentArtistAlbumsIds.equals(dto.getAlbumsIds())) {
			Set<Long> artistAlbumsIdsForClearing = CollectionUtil.getExceededFromFirst(currentArtistAlbumsIds, dto.getAlbumsIds());
			this.updateClearedOrDeletedAlbumsWithDefaultArtist(artistAlbumsIdsForClearing);
			artist.getAlbums().clear();
			artist.setAlbums(this.albumRepository.findAllByIdIn(dto.getAlbumsIds()));
		}

		//Songs
		Set<Long> currentArtistSongsIds = artist.getSongs().stream()
			.map(Song::getId).collect(Collectors.toSet());
		//set default for exceeded
		if (!currentArtistSongsIds.equals(dto.getSongsIds())) {
			Set<Long> artistSongsIdsForClearing = CollectionUtil.getExceededFromFirst(currentArtistSongsIds, dto.getSongsIds());
			this.updateClearedOrDeletedSongsWithDefaultArtist(artistSongsIdsForClearing);
			artist.getSongs().clear();
			artist.setSongs(this.songRepository.findAllByIdIn(dto.getSongsIds()));
		}

		this.artistRepository.save(artist);
		log.info("Artist {} was updated: {}", artist.getId(), artist);
		return this.artistConverter.toDTO(artist);
	}

	@Transactional
	public Artist getEntityById(Long id) throws ArtistNotFoundException {
		return this.artistRepository.findById(id)
			.orElseThrow(ArtistNotFoundException::new);
	}

	@Transactional
	public ArtistDTO getById(Long id) throws ArtistNotFoundException {
		return this.artistConverter.toDTO(this.getEntityById(id));
	}

	@Transactional
	public Artist getDefaultArtist() {
		return this.artistRepository.findById(DEFAULT_ARTIST_ID).get();
	}

	@Transactional
	public void delete(Long id) {
		try {
			Artist artist = this.getEntityById(id);
			this.updateClearedOrDeletedAlbumsWithDefaultArtist(
				artist.getAlbums().stream().map(Album::getId).collect(Collectors.toSet())
			);
			this.updateClearedOrDeletedSongsWithDefaultArtist(
				artist.getSongs().stream().map(Song::getId).collect(Collectors.toSet())
			);
			this.artistRepository.delete(artist);
			log.info("Artist with id {} was deleted successfully", id);
		} catch (NotFoundException e) {
			log.warn(e.getMessage());
		}
	}

	private void updateClearedOrDeletedAlbumsWithDefaultArtist(Set<Long> artistAlbumsIdsForClearing) {
		List<Album> artistAlbumsForClearing = this.albumRepository.findAllByIdIn(artistAlbumsIdsForClearing);
		this.albumRepository.saveAll(
			artistAlbumsForClearing.stream()
				.peek(_album -> _album.setArtist(this.getDefaultArtist()))
				.collect(Collectors.toSet())
		);
	}

	private void updateClearedOrDeletedSongsWithDefaultArtist(Set<Long> artistSongsIdsForClearing) {
		List<Song> artistSongsForClearing = this.songRepository.findAllByIdIn(artistSongsIdsForClearing);
		this.songRepository.saveAll(
			artistSongsForClearing.stream()
				.peek(_song -> _song.setArtist(this.getDefaultArtist()))
				.collect(Collectors.toSet())
		);
	}

	@Transactional
	public Artist getOrCreateArtistOrDefault(ArtistDTO dto) throws ArtistNotFoundException, ArtistCreationException {
		Artist artistForSong = null;
		if (dto != null) {
			if (dto.getId() == null) {
				artistForSong = this.createEntity(dto);
			} else {
				artistForSong = this.getEntityById(dto.getId());
			}
		} else {
			artistForSong = this.getDefaultArtist();
		}
		return artistForSong;
	}

}
