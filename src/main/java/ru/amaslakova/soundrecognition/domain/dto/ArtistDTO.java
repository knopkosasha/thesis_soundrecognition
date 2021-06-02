package ru.amaslakova.soundrecognition.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Artist.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO implements Serializable {

	private Long id;
	private String name;
	private Set<Long> albumsIds = new HashSet<>();
	private Set<Long> songsIds = new HashSet<>();
}
