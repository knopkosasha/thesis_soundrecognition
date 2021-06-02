package ru.amaslakova.soundrecognition.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.common.behavior.Converter;
import ru.amaslakova.soundrecognition.domain.dto.albumcover.AlbumCoverDTO;
import ru.amaslakova.soundrecognition.domain.model.AlbumCover;

/**
 * Converter for AlbumCover.
 */
@Service
@RequiredArgsConstructor
public class AlbumCoverConverter implements Converter<AlbumCover, AlbumCoverDTO> {

	@Override
	public AlbumCover fromDTO(AlbumCoverDTO dto) {
		AlbumCover cover = new AlbumCover();
		cover.setId(dto.getId());
		cover.setContent(dto.getContent());
		cover.setContentType(dto.getContentType());
		cover.setFilename(dto.getFilename());
		return cover;
	}

	@Override
	public AlbumCoverDTO toDTO(AlbumCover entity) {
		AlbumCoverDTO dto = new AlbumCoverDTO();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setContentType(entity.getContentType());
		dto.setFilename(entity.getFilename());
		return dto;
	}
}
