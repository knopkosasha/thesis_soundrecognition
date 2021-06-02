package ru.amaslakova.soundrecognition.exception.artist;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Artist Update API exception.
 */
public class ArtistUpdateException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "artist_update_error";

	public ArtistUpdateException() {
		super(ERROR_TEXT);
	}

	public ArtistUpdateException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
