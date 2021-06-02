package ru.amaslakova.soundrecognition.exception.albumcover;

import ru.amaslakova.soundrecognition.exception.APIException;

/**
 * Возникает при попытке загрузить файл неверного формата.
 */
public class UnsupportedFileFormatException extends APIException {
	/**
	 * Текст ошибки.
	 */
	public final static String ERROR_TEXT = "file_format_not_supported";

	public UnsupportedFileFormatException() {
		super(ERROR_TEXT);
	}
}
