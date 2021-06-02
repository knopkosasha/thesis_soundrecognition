package ru.amaslakova.soundrecognition.domain.repository.song;

import org.springframework.data.jpa.domain.Specification;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Spring Specifications for Song filters.
 */
public final class SongSpecs {

	private SongSpecs() {
	}

	public static Specification<Song> artistIn(List<Artist> artists) {
		if (artists == null) {
			return null;
		}
		return new Specification<Song>() {
			@Override
			public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery,
			                             CriteriaBuilder criteriaBuilder) {
				return root.get("artist").in(artists);
			}
		};
	}

	public static Specification<Song> artistIdsIn(Long[] artistsIds) {
		if (artistsIds == null) {
			return null;
		}
		return artistIdsIn(Arrays.asList(artistsIds));
	}

	public static Specification<Song> artistIdsIn(List<Long> artistsIds) {
		if (artistsIds == null) {
			return null;
		}
		return new Specification<Song>() {
			@Override
			public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery,
			                             CriteriaBuilder criteriaBuilder) {
				return root.get("artist").get("id").in(artistsIds);
			}
		};
	}

	public static Specification<Song> albumIn(List<Album> albums) {
		if (albums == null) {
			return null;
		}
		return new Specification<Song>() {
			@Override
			public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery,
			                             CriteriaBuilder criteriaBuilder) {
				return root.get("album").in(albums);
			}
		};
	}

	public static Specification<Song> albumIdsIn(Long[] albumsIds) {
		if (albumsIds == null) {
			return null;
		}
		return albumIdsIn(Arrays.asList(albumsIds));
	}

	public static Specification<Song> albumIdsIn(List<Long> albumsIds) {
		if (albumsIds == null) {
			return null;
		}
		return new Specification<Song>() {
			@Override
			public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery,
			                             CriteriaBuilder criteriaBuilder) {
				return root.get("album").get("id").in(albumsIds);
			}
		};
	}

	public static Specification<Song> genreIn(Genre[] genres) {
		if (genres == null) {
			return null;
		}
		return genreIn(Arrays.asList(genres));
	}

	public static Specification<Song> genreIn(List<Genre> genres) {
		if (genres == null) {
			return null;
		}
		return new Specification<Song>() {
			@Override
			public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery,
			                             CriteriaBuilder criteriaBuilder) {
				return root.get("genre").in(genres);
			}
		};
	}

}
