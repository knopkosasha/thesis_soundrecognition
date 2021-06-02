package ru.amaslakova.soundrecognition.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.amaslakova.soundrecognition.domain.dto.AlbumDTO;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.service.AlbumService;

/**
 * REST controller for spellbooks additional managing.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
@Api(value = "/api/album", tags = {"album"})
public class AlbumManageController {

	private final AlbumService albumService;

	@PostMapping("/{id}/add/{songId}")
	@ApiOperation("Add song to album")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 404,
			message = "Entities with given params not found. Possible error key\n" +
				"* " + NotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public AlbumDTO addSongToAlbum(@PathVariable("id") Long albumId, @PathVariable("songId") Long songId)
		throws NotFoundException {

		log.debug("REST request to add Song {} to Album {}", songId, albumId);
		return this.albumService.addSongToAlbum(albumId, songId);
	}

	@PostMapping("/{id}/remove/{songId}")
	@ApiOperation("Remove song from album")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 404,
			message = "Album with given id not found. Possible error key\n" +
				"* " + NotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public AlbumDTO removeSongFromAlbum(@PathVariable("id") Long albumId, @PathVariable("songId") Long songId)
		throws NotFoundException {

		log.debug("REST request to remove Song {} from Album {}", songId, albumId);
		return this.albumService.removeSongFromAlbum(songId, albumId);
	}
}
