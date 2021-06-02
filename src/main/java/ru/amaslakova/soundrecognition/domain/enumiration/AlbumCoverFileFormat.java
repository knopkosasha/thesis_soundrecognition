package ru.amaslakova.soundrecognition.domain.enumiration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Supported AlbumCovers file formats.
 */
@AllArgsConstructor
public enum AlbumCoverFileFormat {
	/**
	 * JPEG.
	 */
	JPEG("image/jpeg", ".jpeg"),
	/**
	 * JPG.
	 */
	JPG("image/jpg", ".jpg"),
	/**
	 * PNG.
	 */
	PNG("image/png", ".png"),
	/**
	 * BMP.
	 */
	BMP("image/bmp", ".bmp");

	@Getter
	private String contentType;

	@Getter
	private String extension;

	/**
	 * Map contentType to extension.
	 */
	public static Map<String, String> contentTypesToExtensions = Collections.unmodifiableMap(
		Arrays.stream(values())
			.collect(Collectors.toMap(AlbumCoverFileFormat::getContentType, AlbumCoverFileFormat::getExtension))
	);
}
