package ru.amaslakova.soundrecognition.controller.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.amaslakova.soundrecognition.domain.dto.AlbumDTO;
import ru.amaslakova.soundrecognition.exception.APIException;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.album.AlbumCreationException;
import ru.amaslakova.soundrecognition.exception.album.AlbumNotFoundException;
import ru.amaslakova.soundrecognition.exception.album.AlbumUpdateException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistCreationException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistNotFoundException;
import ru.amaslakova.soundrecognition.service.AlbumService;

import java.util.Map;

/**
 * CRUD for Album.
 */
@Slf4j
@RestController
@RequestMapping("/api/album")
@Api(value = "/api/album", tags = "album")
@RequiredArgsConstructor
public class AlbumResource {

	private final AlbumService albumService;

	@PostMapping
	@ApiOperation("Create new album")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 400,
			message = "Bad request. Possible error key\n" +
				"* " + AlbumCreationException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public AlbumDTO create(AlbumDTO dto) throws AlbumCreationException {
		log.debug("REST request to create Album : {}", dto);
		return this.albumService.createAlbum(dto);
	}

	@PutMapping("/{id}")
	@ApiOperation("Update album")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 400,
			message = "Bad request. Possible error key\n" +
				"* " + AlbumUpdateException.ERROR_TEXT + "\n" +
				"* " + ArtistCreationException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		),
		@ApiResponse(
			code = 404,
			message = "Entities with these ids not found. Possible error key\n" +
				"* " + NotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public AlbumDTO update(@PathVariable("id") Long id, @RequestBody AlbumDTO dto)
		throws AlbumNotFoundException, ArtistCreationException, ArtistNotFoundException, AlbumUpdateException {
		dto.setId(id);
		log.debug("REST request to update Artist : {}", dto);
		return this.albumService.updateAlbum(dto);
	}

	@GetMapping("/{id}")
	@ApiOperation("Get album by id")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 404,
			message = "Song with this id not found. Possible error key\n" +
				"* " + AlbumNotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public AlbumDTO getById(@PathVariable("id") Long id) throws AlbumNotFoundException {
		log.debug("REST request to get Album {}", id);
		return this.albumService.getById(id);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Delete album by id")
	@ApiResponses({
		@ApiResponse(
			code = 204,
			message = "Success"
		)
	})
	public void delete(@PathVariable("id") Long id) {
		log.debug("REST request to delete Album {}", id);
		this.albumService.delete(id);
	}

	@ExceptionHandler({ArtistCreationException.class, AlbumCreationException.class,
		AlbumUpdateException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handle400(APIException e) {
		log.error("Error during process request:", e);
		return e.getErrors();
	}
}
