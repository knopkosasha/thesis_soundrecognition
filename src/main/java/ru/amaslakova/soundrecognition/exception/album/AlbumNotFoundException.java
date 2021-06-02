package ru.amaslakova.soundrecognition.exception.album;

import ru.amaslakova.soundrecognition.exception.NotFoundException;

/**
 * NotFound exception for Album with pre-defined model name for message.
 */
public class AlbumNotFoundException extends NotFoundException {

	protected static final String MODEL_NAME = "album";

	public AlbumNotFoundException() {
		super(MODEL_NAME);
	}
}
