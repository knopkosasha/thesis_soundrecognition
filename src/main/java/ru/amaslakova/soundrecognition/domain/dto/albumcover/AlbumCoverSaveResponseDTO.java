package ru.amaslakova.soundrecognition.domain.dto.albumcover;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Response for saving AlbumCover.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCoverSaveResponseDTO implements Serializable {
	private Long id;
}
