package ru.amaslakova.soundrecognition.exception.song;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Song Creation API exception.
 */
public class SongCreationException extends APIException {

	/**
	 * Error message.
	 */
	public static final String ERROR_TEXT = "song_creation_error";

	public SongCreationException() {
		super(ERROR_TEXT);
	}

	public SongCreationException(String additionalMessage) {
		super(ERROR_TEXT, additionalMessage);
	}
}
