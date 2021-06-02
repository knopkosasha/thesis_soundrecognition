package ru.amaslakova.soundrecognition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amaslakova.soundrecognition.common.util.CollectionUtil;
import ru.amaslakova.soundrecognition.domain.dto.AlbumDTO;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.AlbumCover;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.repository.AlbumCoverRepository;
import ru.amaslakova.soundrecognition.domain.repository.AlbumRepository;
import ru.amaslakova.soundrecognition.domain.repository.song.SongRepository;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.album.AlbumCreationException;
import ru.amaslakova.soundrecognition.exception.album.AlbumNotFoundException;
import ru.amaslakova.soundrecognition.exception.album.AlbumUpdateException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistCreationException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistNotFoundException;
import ru.amaslakova.soundrecognition.exception.song.SongNotFoundException;
import ru.amaslakova.soundrecognition.service.converter.AlbumConverter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for Album.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {

	private static final Long DEFAULT_ALBUM_ID = 0L;

	private final AlbumConverter albumConverter;

	private final AlbumRepository albumRepository;
	private final AlbumCoverRepository albumCoverRepository;
	private final SongRepository songRepository;

	private final ArtistService artistService;

	@Transactional
	public Album createEntity(AlbumDTO albumDTO) throws AlbumCreationException {
		if (albumDTO.getId() != null) {
			throw new AlbumCreationException("album id exists");
		}
		Album newAlbum = this.albumConverter.fromDTO(albumDTO);
		if (albumDTO.getCoverId() != null) {
			AlbumCover albumCover = this.albumCoverRepository.findById(albumDTO.getCoverId()).orElse(null);
			newAlbum.setCover(albumCover);
		}
		this.albumRepository.save(newAlbum);
		log.info("Created new Album {}", newAlbum);
		return newAlbum;
	}

	@Transactional
	public AlbumDTO createAlbum(AlbumDTO albumDTO) throws AlbumCreationException {
		return this.albumConverter.toDTO(this.createEntity(albumDTO));
	}

	@Transactional
	public AlbumDTO updateAlbum(AlbumDTO dto) throws AlbumNotFoundException,
		ArtistNotFoundException, AlbumUpdateException, ArtistCreationException {

		if (dto.getId() == null) {
			throw new AlbumUpdateException("album id is null");
		}
		Album album = this.albumRepository.findById(dto.getId())
			.orElseThrow(AlbumNotFoundException::new);
		album.setName(dto.getName());
		album.setDescription(dto.getDescription());

		album.setArtist(this.artistService.getOrCreateArtistOrDefault(dto.getArtist()));

		AlbumCover cover = album.getCover();
		if (dto.getCoverId() != null) {
			cover = this.albumCoverRepository.findById(dto.getCoverId()).orElse(null);
		}
		album.setCover(cover);

		Set<Long> currentAlbumSongsIds = album.getSongs().stream()
			.map(Song::getId).collect(Collectors.toSet());
		//set default for exceeded
		if (!currentAlbumSongsIds.equals(dto.getSongsIds())) {
			Set<Long> artistSongsIdsForClearing = CollectionUtil.getExceededFromFirst(currentAlbumSongsIds, dto.getSongsIds());
			this.updateClearedOrDeletedSongsWithDefaultAlbum(artistSongsIdsForClearing);
			album.getSongs().clear();
			album.setSongs(this.songRepository.findAllByIdIn(dto.getSongsIds()));
		}
		this.albumRepository.save(album);
		log.info("Album {} was updated {}", album.getId(), album);
		return this.albumConverter.toDTO(album);
	}

	@Transactional
	public AlbumDTO addSongToAlbum(Long albumId, Long songId) throws AlbumNotFoundException, SongNotFoundException {
		Album album = this.albumRepository.findById(albumId)
			.orElseThrow(AlbumNotFoundException::new);
		Song desiredSong = this.songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
		album.addSong(desiredSong);
		desiredSong.setAlbum(album);
		this.songRepository.save(desiredSong);
		Album updated = this.albumRepository.save(album);
		log.info("Song: {}:{} was added to Album {}",
			desiredSong.getId(), desiredSong.getName(), updated.getId());
		return this.albumConverter.toDTO(updated);
	}

	@Transactional
	public AlbumDTO removeSongFromAlbum(Long albumId, Long songId) throws AlbumNotFoundException {
		Album album = this.albumRepository.findById(albumId)
			.orElseThrow(AlbumNotFoundException::new);
		try {
			Song desiredSong = this.songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
			album.removeSong(desiredSong);
			desiredSong.setAlbum(this.getDefaultAlbum());
			this.songRepository.save(desiredSong);
			Album updated = this.albumRepository.save(album);
			log.info("Song: {}:{} was removed from Album {}",
				desiredSong.getId(), desiredSong.getName(), updated.getId());
			return this.albumConverter.toDTO(updated);
		} catch (SongNotFoundException e) {
			log.warn("Song with name {} was not found, nothing to remove from spellbook", songId);
			return this.albumConverter.toDTO(album);
		}
	}

	@Transactional
	public Album getEntityById(Long id) throws AlbumNotFoundException {
		return this.albumRepository.findById(id)
			.orElseThrow(AlbumNotFoundException::new);
	}

	@Transactional
	public AlbumDTO getById(Long id) throws AlbumNotFoundException {
		return this.albumConverter.toDTO(this.getEntityById(id));
	}

	@Transactional
	public Album getDefaultAlbum() {
		return this.albumRepository.findById(DEFAULT_ALBUM_ID).get();
	}

	@Transactional
	public List<AlbumDTO> getByArtist(Long artistId) throws ArtistNotFoundException {
		Artist artist = this.artistService.getEntityById(artistId);
		List<Album> albums = this.albumRepository.findAllByArtist(artist);
		return albums.stream()
			.map(this.albumConverter::toDTO)
			.collect(Collectors.toList());
	}

	@Transactional
	public void delete(Long id) {
		try {
			Album album = this.getEntityById(id);
			this.updateClearedOrDeletedSongsWithDefaultAlbum(
				album.getSongs().stream().map(Song::getId).collect(Collectors.toSet())
			);
			this.albumRepository.delete(album);
			log.info("Album with id {} was deleted successfully", id);
		} catch (NotFoundException e) {
			log.warn(e.getMessage());
		}
	}

	private void updateClearedOrDeletedSongsWithDefaultAlbum(Set<Long> albumSongsIdsForClearing) {
		List<Song> artistSongsForClearing = this.songRepository.findAllByIdIn(albumSongsIdsForClearing);
		this.songRepository.saveAll(
			artistSongsForClearing.stream()
				.peek(_song -> _song.setAlbum(this.getDefaultAlbum()))
				.collect(Collectors.toSet())
		);
	}

	@Transactional
	public Album getOrCreateAlbumOrDefault(AlbumDTO dto) throws AlbumNotFoundException, AlbumCreationException {
		Album albumForSong = null;
		if (dto != null) {
			if (dto.getId() == null) {
				albumForSong = this.createEntity(dto);
			} else {
				albumForSong = this.getEntityById(dto.getArtist().getId());
			}
		} else {
			albumForSong = this.getDefaultAlbum();
		}
		return albumForSong;
	}
}
