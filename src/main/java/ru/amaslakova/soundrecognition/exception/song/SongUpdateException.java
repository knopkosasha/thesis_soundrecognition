package ru.amaslakova.soundrecognition.exception.song;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Song Update API exception.
 */
public class SongUpdateException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "song_update_error";

	public SongUpdateException() {
		super(ERROR_TEXT);
	}

	public SongUpdateException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
