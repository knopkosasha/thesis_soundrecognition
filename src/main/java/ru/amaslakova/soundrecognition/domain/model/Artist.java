package ru.amaslakova.soundrecognition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Artist data entity.
 */
@Data
@Entity
@Table(name = "artist")
@AllArgsConstructor
@NoArgsConstructor
public class Artist implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Album> albums = new LinkedList<>();

	@OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Song> songs = new LinkedList<>();

	@Override
	public String toString() {
		return "Artist{" +
			"id=" + getId() +
			", name='" + getName() + "'" +
			"}";
	}

	public Artist addSong(Song song) {
		this.songs.add(song);
		return this;
	}

	public Artist removeSong(Song song) {
		this.songs.remove(song);
		return this;
	}

	public Artist addAlbum(Album album) {
		this.albums.add(album);
		return this;
	}

	public Artist removeAlbum(Album album) {
		this.albums.remove(album);
		return this;
	}
}
