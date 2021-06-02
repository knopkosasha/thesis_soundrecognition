package ru.amaslakova.soundrecognition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Album data entity.
 */
@Data
@Entity
@Table(name = "album")
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;

	@JoinColumn(name = "cover_id")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private AlbumCover cover;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
	private List<Song> songs = new LinkedList<>();

	@Override
	public String toString() {
		return "Album{" +
			"id=" + getId() +
			", name='" + getName() + "'" +
			", artist='" + getArtist().getId() + "'" +
			", cover='" + (getCover() != null ? getCover().getId().toString() : getCover()) + "'" +
			"}";
	}

	public Album addSong(Song song) {
		this.songs.add(song);
		return this;
	}

	public Album removeSong(Song song) {
		this.songs.remove(song);
		return this;
	}
}
