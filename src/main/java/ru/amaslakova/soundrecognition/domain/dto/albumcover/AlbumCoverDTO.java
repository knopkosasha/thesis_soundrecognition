package ru.amaslakova.soundrecognition.domain.dto.albumcover;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for AlbumCover.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCoverDTO implements Serializable {

	private Long id;
	private String contentType;
	private byte[] content;
	private String filename;
}
