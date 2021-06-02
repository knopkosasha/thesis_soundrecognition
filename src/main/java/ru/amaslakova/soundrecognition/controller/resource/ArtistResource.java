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
import ru.amaslakova.soundrecognition.domain.dto.ArtistDTO;
import ru.amaslakova.soundrecognition.exception.APIException;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistCreationException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistNotFoundException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistUpdateException;
import ru.amaslakova.soundrecognition.service.ArtistService;

import java.util.Map;

/**
 * CRUD for Artist.
 */
@Slf4j
@RestController
@RequestMapping("/api/artist")
@Api(value = "/api/artist", tags = "artist")
@RequiredArgsConstructor
public class ArtistResource {

	private final ArtistService artistService;

	@PostMapping
	@ApiOperation("Create new artist")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 400,
			message = "Bad request. Possible error key\n" +
				"* " + ArtistCreationException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public ArtistDTO create(ArtistDTO dto) throws ArtistCreationException {
		log.debug("REST request to create Artist : {}", dto);
		return this.artistService.createArtist(dto);
	}

	@PutMapping("/{id}")
	@ApiOperation("Update artist")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 400,
			message = "Bad request. Possible error key\n" +
				"* " + ArtistUpdateException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		),
		@ApiResponse(
			code = 404,
			message = "Entities with these ids not found. Possible error key\n" +
				"* " + NotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public ArtistDTO update(@PathVariable("id") Long id, @RequestBody ArtistDTO artist)
		throws ArtistNotFoundException, ArtistUpdateException {
		artist.setId(id);
		log.debug("REST request to update Artist : {}", artist);
		return this.artistService.updateArtist(artist);
	}

	@GetMapping("/{id}")
	@ApiOperation("Get artist by id")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success"
		),
		@ApiResponse(
			code = 404,
			message = "Song with this id not found. Possible error key\n" +
				"* " + ArtistNotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map"
		)
	})
	public ArtistDTO getById(@PathVariable("id") Long id) throws ArtistNotFoundException {
		log.debug("REST request to get Artist {}", id);
		return this.artistService.getById(id);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Delete artist by id")
	@ApiResponses({
		@ApiResponse(
			code = 204,
			message = "Success"
		)
	})
	public void delete(@PathVariable("id") Long id) {
		log.debug("REST request to delete Artist {}", id);
		this.artistService.delete(id);
	}

	@ExceptionHandler({ArtistUpdateException.class, ArtistCreationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handle400(APIException e) {
		log.error("Error during process request:", e);
		return e.getErrors();
	}
}
