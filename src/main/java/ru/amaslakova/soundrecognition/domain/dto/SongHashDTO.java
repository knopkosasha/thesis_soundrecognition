package ru.amaslakova.soundrecognition.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for SongHash.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongHashDTO implements Serializable {

	private Long id;
	private String hash;
	private Long songId;
}
