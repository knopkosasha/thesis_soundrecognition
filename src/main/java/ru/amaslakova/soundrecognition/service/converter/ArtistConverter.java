package ru.amaslakova.soundrecognition.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.common.behavior.Converter;
import ru.amaslakova.soundrecognition.domain.dto.ArtistDTO;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Artist;
import ru.amaslakova.soundrecognition.domain.model.Song;

import java.util.stream.Collectors;

/**
 * Converter for Artist.
 */
@Service
@RequiredArgsConstructor
public class ArtistConverter implements Converter<Artist, ArtistDTO> {

	@Override
	public Artist fromDTO(ArtistDTO dto) {
		Artist artist = new Artist();
		artist.setId(dto.getId());
		artist.setName(dto.getName());
		return artist;
	}

	@Override
	public ArtistDTO toDTO(Artist entity) {
		ArtistDTO dto = new ArtistDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAlbumsIds(entity.getAlbums().stream()
			.map(Album::getId)
			.collect(Collectors.toSet()));
		dto.setSongsIds(entity.getSongs().stream()
			.map(Song::getId)
			.collect(Collectors.toSet()));
		return dto;
	}
}
