package ru.amaslakova.soundrecognition.exception.album;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Album Creation API exception.
 */
public class AlbumCreationException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "album_creation_error";

	public AlbumCreationException() {
		super(ERROR_TEXT);
	}

	public AlbumCreationException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
