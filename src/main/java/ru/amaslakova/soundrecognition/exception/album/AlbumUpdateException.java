package ru.amaslakova.soundrecognition.exception.album;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Album Update API exception.
 */
public class AlbumUpdateException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "album_update_error";

	public AlbumUpdateException() {
		super(ERROR_TEXT);
	}

	public AlbumUpdateException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
