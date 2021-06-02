package ru.amaslakova.soundrecognition.controller.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.amaslakova.soundrecognition.domain.dto.SongDTO;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;
import ru.amaslakova.soundrecognition.exception.APIException;
import ru.amaslakova.soundrecognition.exception.NotFoundException;
import ru.amaslakova.soundrecognition.exception.album.AlbumCreationException;
import ru.amaslakova.soundrecognition.exception.artist.ArtistCreationException;
import ru.amaslakova.soundrecognition.exception.song.SongCreationException;
import ru.amaslakova.soundrecognition.exception.song.SongNotFoundException;
import ru.amaslakova.soundrecognition.exception.song.SongUpdateException;
import ru.amaslakova.soundrecognition.service.SongService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * CRUD API for Song.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
@Api(value = "/api/song", tags = "song")
public class SongResource {
    //TODO: запилить поиск на основе Spring Specifications (типа фильтры)

    private final SongService songService;

    @PostMapping
    @ApiOperation("Create new Song")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Success"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad request. Possible error key\n" +
                            "* " + SongCreationException.ERROR_TEXT + "\n" +
                            "* " + AlbumCreationException.ERROR_TEXT + "\n" +
                            "* " + ArtistCreationException.ERROR_TEXT + "\n",
                    response = String.class, responseContainer = "Map"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Additional entities with these ids not found. Possible error key\n" +
                            "* " + NotFoundException.ERROR_TEXT,
                    response = String.class, responseContainer = "Map"
            )
    })
    public SongDTO create(@RequestBody SongDTO song)
            throws Exception {

        log.debug("REST request to create Song : {}", song);
        return this.songService.create(song);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update song")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Success"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad request. Possible error key\n" +
                            "* " + SongUpdateException.ERROR_TEXT + "\n" +
                            "* " + AlbumCreationException.ERROR_TEXT + "\n" +
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
    public SongDTO update(@PathVariable("id") Long id, @RequestBody SongDTO song)
            throws Exception {

        song.setId(id);
        log.debug("REST request to update Spell : {}", song);
        return this.songService.update(song);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get song by id")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Success"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Song with this id not found. Possible error key\n" +
                            "* " + SongNotFoundException.ERROR_TEXT,
                    response = String.class, responseContainer = "Map"
            )
    })
    public SongDTO getById(@PathVariable("id") Long id) throws SongNotFoundException {
        log.debug("REST request to get Song {}", id);
        return this.songService.getById(id);
    }

    @GetMapping("/")
    @ApiOperation("Get songs by filter")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Success"
            )
    })
    public List<SongDTO> list(@RequestParam(value = "artistIds", required = false) Long[] artistIds,
                              @RequestParam(value = "albumId", required = false) Long[] albumIds,
                              @RequestParam(value = "genre", required = false) Genre[] genres) {

        log.debug("REST request to get Songs by filter {}",
                Arrays.toString(artistIds) + Arrays.toString(albumIds) + Arrays.toString(genres)
        );
        return this.songService.list(artistIds, albumIds, genres);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete song by id")
    @ApiResponses({
            @ApiResponse(
                    code = 204,
                    message = "Success"
            )
    })
    public void delete(@PathVariable("id") Long id) {
        log.debug("REST request to delete Song {}", id);
        this.songService.delete(id);
    }

    @GetMapping
    @ApiOperation("Compare songs")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Success"
            )
    })
    public double compare (@PathVariable("partId") Long partId, @PathVariable("fullId") Long fullId) throws Exception {
        return this.songService.compareById(partId,fullId);
    }

    @ExceptionHandler({SongCreationException.class, SongUpdateException.class,
            AlbumCreationException.class, ArtistCreationException.class, Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle400(APIException e) {
        log.error("Error during process request:", e);
        return e.getErrors();
    }
}
