package ru.amaslakova.soundrecognition.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.common.behavior.Converter;
import ru.amaslakova.soundrecognition.domain.dto.SongHashDTO;
import ru.amaslakova.soundrecognition.domain.model.SongHash;

/**
 * Converter for SongHash.
 */
@Service
@RequiredArgsConstructor
public class SongHashConverter implements Converter<SongHash, SongHashDTO> {

	@Override
	public SongHash fromDTO(SongHashDTO dto) {
		SongHash hash = new SongHash();
		hash.setId(dto.getId());
		hash.setHash(dto.getHash());
		return hash;
	}

	@Override
	public SongHashDTO toDTO(SongHash entity) {
		SongHashDTO dto = new SongHashDTO();
		dto.setId(entity.getId());
		dto.setHash(entity.getHash());
		dto.setSongId(entity.getSong().getId());
		return dto;
	}
}
