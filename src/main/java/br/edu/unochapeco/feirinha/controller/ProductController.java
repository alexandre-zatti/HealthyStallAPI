package br.edu.unochapeco.feirinha.controller;

import br.edu.unochapeco.feirinha.dto.ProductGetDto;
import br.edu.unochapeco.feirinha.dto.ProductPostDto;
import br.edu.unochapeco.feirinha.exception.ProductNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.mapper.ProductGetMapper;
import br.edu.unochapeco.feirinha.mapper.ProductPostMapper;
import br.edu.unochapeco.feirinha.service.ProductService;
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

@Tag(name = "Products", description = "Group of endpoints responsible for dealing with Products operations")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = ProductGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductGetDto> createProduct(@RequestBody @Valid ProductPostDto productPostDto){
        var product = ProductPostMapper.INSTANCE.toProduct(productPostDto);

        var savedProduct = this.productService.createProduct(product);

        return new ResponseEntity<>(
                ProductGetMapper.INSTANCE.toProductGetDto(savedProduct),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class)),
                            mediaType = "application/json"
                    )
            }),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProductGetDto>> getAllProducts() {

        return new ResponseEntity<>(
                ProductGetMapper.INSTANCE.toListProductGetDto(this.productService.getAllProducts()),
                HttpStatus.OK
        );
    }
    @Operation(summary = "Get specific product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProductGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductGetDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {

        return new ResponseEntity<>(
                ProductGetMapper.INSTANCE.toProductGetDto(this.productService.getProductById(id)),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update specific product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProductGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductGetDto> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> fields)
            throws ProductNotFoundException, UniqueUsernameValidationException {

        var updatedProduct = this.productService.updateProduct(id, fields);

        return new ResponseEntity<>(
                ProductGetMapper.INSTANCE.toProductGetDto(updatedProduct),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete specific product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        this.productService.deleteProduct(id);
    }
}
