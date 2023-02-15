package br.edu.unochapeco.feirinha.controller;

import br.edu.unochapeco.feirinha.dto.FeiranteGetDto;
import br.edu.unochapeco.feirinha.exception.NoActiveFeiranteException;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.mapper.FeiranteGetMapper;
import br.edu.unochapeco.feirinha.service.FeiranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Feirantes", description = "Group of endpoints responsible for dealing with Feirantes operations")
@RestController
@RequestMapping("/api/feirantes")
public class FeiranteController {

    private final FeiranteService feiranteService;

    public FeiranteController(FeiranteService feiranteService) {
        this.feiranteService = feiranteService;
    }

    @Operation(summary = "Set the current feirante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FeiranteGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PostMapping(value = "/{personId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FeiranteGetDto> setFeirante(@PathVariable Long personId) throws PersonNotFoundException {

        var newFeirante = this.feiranteService.setNewFeirante(personId);

        return new ResponseEntity<>(
                FeiranteGetMapper.INSTANCE.toFeiranteGetDto(newFeirante),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get current feirante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FeiranteGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @GetMapping(value = "/current",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FeiranteGetDto> getCurrentFeirante() throws NoActiveFeiranteException {

        var currentFeirante = this.feiranteService.getCurrentFeirante();

        return new ResponseEntity<>(
                FeiranteGetMapper.INSTANCE.toFeiranteGetDto(currentFeirante),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get history of all feirantes ever assigned")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = FeiranteGetDto.class)),
                            mediaType = "application/json"
                    )
            }),
    })
    @GetMapping(value = "/history",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<FeiranteGetDto>> getAllFeiranteHistory() {

        var feirantesHistory = this.feiranteService.getFeirantesHistory();

        return new ResponseEntity<>(
                FeiranteGetMapper.INSTANCE.toListFeiranteGetDto(feirantesHistory),
                HttpStatus.OK
        );
    }
}
