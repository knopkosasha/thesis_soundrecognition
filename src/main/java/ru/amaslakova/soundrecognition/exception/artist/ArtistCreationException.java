package ru.amaslakova.soundrecognition.exception.artist;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Artist Creation API exception.
 */
public class ArtistCreationException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "artist_creation_error";

	public ArtistCreationException() {
		super(ERROR_TEXT);
	}

	public ArtistCreationException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
