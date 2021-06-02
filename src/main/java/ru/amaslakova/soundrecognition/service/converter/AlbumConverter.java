package ru.amaslakova.soundrecognition.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.common.behavior.Converter;
import ru.amaslakova.soundrecognition.domain.dto.AlbumDTO;
import ru.amaslakova.soundrecognition.domain.model.Album;
import ru.amaslakova.soundrecognition.domain.model.Song;

import java.util.stream.Collectors;

/**
 * Converter for Album.
 */
@Service
@RequiredArgsConstructor
public class AlbumConverter implements Converter<Album, AlbumDTO> {

	private final AlbumCoverConverter albumCoverConverter;
	private final ArtistConverter artistConverter;

	@Override
	public Album fromDTO(AlbumDTO dto) {
		Album album = new Album();
		album.setId(dto.getId());
		album.setDescription(dto.getDescription());
		album.setName(dto.getName());
		album.setArtist(this.artistConverter.fromDTO(dto.getArtist()));
		return album;
	}

	public AlbumDTO toDTO(Album entity) {
		AlbumDTO dto = new AlbumDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setCoverId(entity.getCover() != null ? this.albumCoverConverter.toDTO(entity.getCover()).getId() : null);
		dto.setArtist(this.artistConverter.toDTO(entity.getArtist()));
		dto.setSongsIds(entity.getSongs().stream()
			.map(Song::getId)
			.collect(Collectors.toSet()));
		return dto;
	}
}
