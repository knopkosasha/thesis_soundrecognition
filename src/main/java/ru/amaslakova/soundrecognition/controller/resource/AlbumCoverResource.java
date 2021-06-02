package ru.amaslakova.soundrecognition.controller.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.amaslakova.soundrecognition.domain.dto.albumcover.AlbumCoverDTO;
import ru.amaslakova.soundrecognition.domain.dto.albumcover.AlbumCoverSaveResponseDTO;
import ru.amaslakova.soundrecognition.domain.enumiration.AlbumCoverFileFormat;
import ru.amaslakova.soundrecognition.exception.APIException;
import ru.amaslakova.soundrecognition.exception.albumcover.AlbumCoverNotFoundException;
import ru.amaslakova.soundrecognition.exception.albumcover.UnsupportedFileFormatException;
import ru.amaslakova.soundrecognition.service.AlbumCoverService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

/**
 * CRUD for Covers.
 */
@RestController
@RequestMapping("/api/album/cover")
@Api(value = "/api/album/cover", tags = "albumCover")
@RequiredArgsConstructor
public class AlbumCoverResource {

	private final AlbumCoverService albumCoverService;

	private Map<String, String> supportedFileFormats = AlbumCoverFileFormat.contentTypesToExtensions;

	@PostMapping("/save")
	@ApiOperation("Create new cover")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success, returns album cover id",
			response = AlbumCoverSaveResponseDTO.class
		),
		@ApiResponse(
			code = 400,
			message = "Bad request. Possible error key:\n" +
				"* bad_input_params",
			response = String.class, responseContainer = "Map"
		),
		@ApiResponse(code = 500, message = "Error during file upload, possible error text:\n" +
			"* " + UnsupportedFileFormatException.ERROR_TEXT,
			response = String.class, responseContainer = "Map")
	})
	public AlbumCoverSaveResponseDTO save(@RequestParam("file") MultipartFile file)
		throws IOException, UnsupportedFileFormatException {

		if (!this.supportedFileFormats.containsKey(file.getContentType())) {
			throw new UnsupportedFileFormatException();
		}

		String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
		String uuid = UUID.randomUUID().toString();
		String fileNameWithUUID =  uuid + '-' + fileName;

		Long id = this.albumCoverService.saveCover(fileNameWithUUID, file.getContentType(), file.getBytes());
		return new AlbumCoverSaveResponseDTO(id);
	}

	@GetMapping("/{id}")
	@ApiOperation("Get cover by id")
	@ApiResponses({
		@ApiResponse(
			code = 200,
			message = "Success",
			response = byte[].class
		),
		@ApiResponse(
			code = 404,
			message = "Cover with this id not found. Possible error key:\n" +
				"* " + AlbumCoverNotFoundException.ERROR_TEXT,
			response = String.class, responseContainer = "Map")
	})
	public void get(@PathVariable("id") Long id, HttpServletResponse response)
		throws AlbumCoverNotFoundException, IOException {

		AlbumCoverDTO file = this.albumCoverService.getById(id);

		response.setContentType(file.getContentType());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");
		response.getOutputStream().write(file.getContent());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({UnsupportedFileFormatException.class})
	public Map<String, String> handle500(APIException e) {
		return e.getErrors();
	}
}
