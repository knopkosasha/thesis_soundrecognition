package ru.amaslakova.soundrecognition.exception.song;

import ru.amaslakova.soundrecognition.exception.NotFoundException;

/**
 * NotFound exception for Song with pre-defined model name for message.
 */
public class SongNotFoundException extends NotFoundException {

	protected static final String MODEL_NAME = "song";

	public SongNotFoundException() {
		super(MODEL_NAME);
	}
}
