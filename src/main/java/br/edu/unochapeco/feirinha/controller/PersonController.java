package br.edu.unochapeco.feirinha.controller;

import br.edu.unochapeco.feirinha.dto.PersonGetDto;
import br.edu.unochapeco.feirinha.dto.PersonPostDto;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.mapper.PersonGetMapper;
import br.edu.unochapeco.feirinha.mapper.PersonPostMapper;
import br.edu.unochapeco.feirinha.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Persons", description = "Group of endpoints responsible for dealing with Persons operations")
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Create a new person")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = PersonPostDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PersonGetDto> createPerson(@RequestBody @Valid PersonPostDto personPostDto) throws UniqueUsernameValidationException {
        var person = PersonPostMapper.INSTANCE.toPerson(personPostDto);

        var savedPerson = this.personService.savePerson(person);

        return new ResponseEntity<>(
                PersonGetMapper.INSTANCE.toPersonGetDto(savedPerson),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all persons")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = PersonGetDto.class)),
                            mediaType = "application/json"
                    )
            }),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PersonGetDto>> getAllPersons() {

        return new ResponseEntity<>(
                PersonGetMapper.INSTANCE.toListPersonGetDto(this.personService.getAllPersons()),
                HttpStatus.OK
        );
    }
    @Operation(summary = "Get specific person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PersonGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PersonGetDto> getPersonById(@PathVariable Long id) throws PersonNotFoundException {

        return new ResponseEntity<>(
                PersonGetMapper.INSTANCE.toPersonGetDto(this.personService.getPersonById(id)),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update specific person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PersonGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PersonGetDto> updatePerson(@PathVariable Long id, @RequestBody Map<String, Object> fields)
            throws PersonNotFoundException, UniqueUsernameValidationException {

        var updatedPerson = this.personService.updatePerson(id, fields);

        return new ResponseEntity<>(
                PersonGetMapper.INSTANCE.toPersonGetDto(updatedPerson),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete specific person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {
                    @Content(schema = @Schema(implementation = PersonGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id){
        this.personService.deletePerson(id);
    }
}
