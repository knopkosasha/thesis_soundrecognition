package ru.amaslakova.soundrecognition.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Album.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO implements Serializable {

	private Long id;
	private String name;
	@JsonIgnoreProperties({"albums", "songs"})
	private ArtistDTO artist;
	private Long coverId;
	private String description;
	@JsonIgnoreProperties({"album"})
	private Set<Long> songsIds = new HashSet<>();
}
