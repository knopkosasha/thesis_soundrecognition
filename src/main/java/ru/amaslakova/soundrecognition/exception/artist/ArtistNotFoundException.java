package ru.amaslakova.soundrecognition.exception.artist;

import ru.amaslakova.soundrecognition.exception.NotFoundException;

/**
 * NotFound exception for Artist with pre-defined model name for message.
 */
public class ArtistNotFoundException extends NotFoundException {

	protected static final String MODEL_NAME = "artist";

	public ArtistNotFoundException() {
		super(MODEL_NAME);
	}
}
