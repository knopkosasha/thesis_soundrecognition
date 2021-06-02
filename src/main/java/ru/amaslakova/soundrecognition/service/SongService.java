package ru.amaslakova.soundrecognition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amaslakova.soundrecognition.domain.dto.SongDTO;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.model.SongHash;
import ru.amaslakova.soundrecognition.domain.repository.SongHashRepository;
import ru.amaslakova.soundrecognition.domain.repository.song.SongRepository;
import ru.amaslakova.soundrecognition.domain.repository.song.SongSpecs;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.song.SongCreationException;
import ru.amaslakova.soundrecognition.exception.song.SongNotFoundException;
import ru.amaslakova.soundrecognition.exception.song.SongUpdateException;
import ru.amaslakova.soundrecognition.fingerprint.AudioFile;
import ru.amaslakova.soundrecognition.fingerprint.hash.CompareHashes;
import ru.amaslakova.soundrecognition.service.converter.SongConverter;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for Song.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    /**
     * Default song name.
     */
    public static final String DEFAULT_SONG_NAME = "Noname";

    private final SongConverter songConverter;

    private final SongRepository songRepository;
    private final SongHashRepository songHashRepository;

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongHashCalculationService songHashCalculationService;

    private final CompareHashes compareHashes;


    @Transactional
    public SongDTO create(SongDTO dto) throws Exception {

        if (dto.getId() != null) {
            throw new SongCreationException("song id exists");
        }
        Song song = this.songConverter.fromDTO(dto);
        if (song.getName() == null || song.getName().isEmpty()) {
            song.setName(DEFAULT_SONG_NAME);
        }
        //Artist
        Artist artist = this.artistService.getOrCreateArtistOrDefault(dto.getArtist());
        artist.addSong(song);
        song.setArtist(artist);
        //Album
        Album album = this.albumService.getOrCreateAlbumOrDefault(dto.getAlbum());
        album.addSong(song);
        song.setAlbum(album);

        Set<SongHash> songHashes = this.songHashCalculationService.calculateSongHashes(song);
        for (SongHash hash : songHashes) {
            hash.setSong(song);
            this.songHashRepository.save(hash);
        }
        song.setHash(songHashes);
        this.songRepository.save(song);
        log.info("Song {} was created: {}", song.getId(), song);
        return this.songConverter.toDTO(song);
    }

    @Transactional
    public SongDTO update(SongDTO dto) throws Exception {

        if (dto.getId() == null) {
            throw new SongUpdateException("song id is null");
        }
        Song song = this.songRepository.findById(dto.getId())
                .orElseThrow(SongNotFoundException::new);
        song.setName(dto.getName());
        if (song.getName() == null || song.getName().isEmpty()) {
            song.setName(DEFAULT_SONG_NAME);
        }
        song.setGenre(dto.getGenre());
        song.setDuration(Duration.of(dto.getDuration(), ChronoUnit.SECONDS));
        song.setHash(this.songHashCalculationService.calculateSongHashes(song));
        //Artist
        Artist artist = this.artistService.getOrCreateArtistOrDefault(dto.getArtist());
        if (!song.getArtist().getId().equals(artist.getId())) {
            artist.addSong(song);
        }
        song.setArtist(artist);
        //Album
        Album album = this.albumService.getOrCreateAlbumOrDefault(dto.getAlbum());
        if (!song.getAlbum().getId().equals(album.getId())) {
            album.addSong(song);
        }
        song.setAlbum(album);

        this.songRepository.save(song);
        log.info("Song {} was updated: {}", song.getId(), song);
        return this.songConverter.toDTO(song);
    }

    @Transactional
    public Song getEntityById(Long id) throws SongNotFoundException {
        return this.songRepository.findById(id)
                .orElseThrow(SongNotFoundException::new);
    }

    @Transactional
    public SongDTO getById(Long id) throws SongNotFoundException {
        return this.songConverter.toDTO(this.getEntityById(id));
    }

    @Transactional
    public List<SongDTO> getAllByIds(Long... ids) {
        Set<Long> songIds = new HashSet<>(Arrays.asList(ids));
        return this.songRepository.findAllByIdIn(songIds).stream()
                .map(this.songConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SongDTO> list(Long[] artistIds, Long[] albumIds, Genre[] genres) {
        List<SongDTO> result = this.songRepository.findAll(
                Specification.where(SongSpecs.artistIdsIn(artistIds))
                        .and(SongSpecs.albumIdsIn(albumIds))
                        .and(SongSpecs.genreIn(genres))
        ).stream().map(this.songConverter::toDTO)
                .collect(Collectors.toList());
        log.info("{} songs were founded by further filters: {}", result.size(),
                Arrays.toString(artistIds) + Arrays.toString(albumIds) + Arrays.toString(genres)
        );
        return result;
    }

    @Transactional
    public void delete(Long id) {
        try {
            Song song = this.getEntityById(id);
            this.songRepository.delete(song);
            log.info("Song with id {} was deleted successfully", id);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
        }
    }

    @Transactional
    public double compareById(Long partId, Long fullId) throws Exception {
        Song part = this.getEntityById(partId);
        Song full = this.getEntityById(fullId);
        File partFile = new File(part.getPath());
        File fullFile = new File(full.getPath());
        AudioFile partAudio = new AudioFile(partFile);
        AudioFile fullAudio = new AudioFile(fullFile);

        return this.compareHashes.compare(partAudio, fullAudio);
    }

}
