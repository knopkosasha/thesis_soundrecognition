package ru.amaslakova.soundrecognition.exception.albumcover;

import ru.amaslakova.soundrecognition.exception.NotFoundException;

/**
 * NotFound exception for AlbumCover with pre-defined model name for message.
 */
public class AlbumCoverNotFoundException extends NotFoundException {

	protected static final String MODEL_NAME = "album_cover";

	public AlbumCoverNotFoundException() {
		super(MODEL_NAME);
	}
}
