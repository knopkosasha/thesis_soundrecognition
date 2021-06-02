package ru.amaslakova.soundrecognition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AlbumCover data entity.
 */
@Data
@Entity
@Table(name = "album_cover")
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCover implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "content")
	private byte[] content;

	@Column(name = "filename")
	private String filename;

	public AlbumCover(String filename, String contentType, byte[] content) {
		this.filename = filename;
		this.contentType = contentType;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Album{" +
			"id=" + getId() +
			", contentType='" + getContentType() + "'" +
			", filename='" + getFilename() + "'" +
			"}";
	}
}
