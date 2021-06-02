package ru.amaslakova.soundrecognition.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.common.behavior.Converter;
import ru.amaslakova.soundrecognition.domain.dto.SongDTO;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.model.SongHash;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Converter for Song.
 */
@Service
@RequiredArgsConstructor
public class SongConverter implements Converter<Song, SongDTO> {

    private final AlbumConverter albumConverter;
    private final ArtistConverter artistConverter;

    @Override
    public Song fromDTO(SongDTO dto) {
        Song song = new Song();
        song.setId(dto.getId());
        song.setName(dto.getName());
        song.setPath(dto.getPath());
        song.setDuration(Duration.of(dto.getDuration(), ChronoUnit.SECONDS));
        song.setGenre(dto.getGenre());
        return song;
    }

    @Override
    public SongDTO toDTO(Song entity) {
        SongDTO dto = new SongDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setGenre(entity.getGenre());
        dto.setDuration(entity.getDuration().getSeconds());
        dto.setHash(entity.getHash().stream()
                .map(SongHash::getId)
                .collect(Collectors.toSet()));
        dto.setArtist(this.artistConverter.toDTO(entity.getArtist()));
        dto.setAlbum(this.albumConverter.toDTO(entity.getAlbum()));
        return dto;
    }
}
