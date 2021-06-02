package ru.amaslakova.soundrecognition.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Song.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO implements Serializable {

    private Long id;
    private String name;
    private String path;
    private Long duration;
    private Genre genre;
    private Set<Long> hash = new HashSet<>();
    private ArtistDTO artist;
    private AlbumDTO album;
}
