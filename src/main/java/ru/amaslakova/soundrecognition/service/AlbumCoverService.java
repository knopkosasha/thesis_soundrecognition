package ru.amaslakova.soundrecognition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amaslakova.soundrecognition.domain.dto.albumcover.AlbumCoverDTO;
import ru.amaslakova.soundrecognition.domain.model.AlbumCover;
import ru.amaslakova.soundrecognition.domain.repository.AlbumCoverRepository;
import ru.amaslakova.soundrecognition.exception.albumcover.AlbumCoverNotFoundException;
import ru.amaslakova.soundrecognition.service.converter.AlbumCoverConverter;

/**
 * Service for AlbumCover.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumCoverService {

	private final AlbumCoverConverter albumCoverConverter;

	private final AlbumCoverRepository albumCoverRepository;

	@Transactional
	public Long saveCover(String fileName, String contentType, byte[] content) {
		AlbumCover cover = new AlbumCover(fileName, contentType, content);
		this.albumCoverRepository.save(cover);
		log.info("New cover was saved {}", cover);
		return cover.getId();
	}

	@Transactional
	public AlbumCoverDTO getById(Long id) throws AlbumCoverNotFoundException {
		AlbumCover cover = this.albumCoverRepository.findById(id)
			.orElseThrow(AlbumCoverNotFoundException::new);
		return this.albumCoverConverter.toDTO(cover);
	}
}
