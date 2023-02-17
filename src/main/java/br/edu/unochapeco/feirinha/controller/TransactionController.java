package br.edu.unochapeco.feirinha.controller;

import br.edu.unochapeco.feirinha.dto.TransactionGetDto;
import br.edu.unochapeco.feirinha.dto.TransactionPostDto;
import br.edu.unochapeco.feirinha.exception.*;
import br.edu.unochapeco.feirinha.mapper.TransactionGetMapper;
import br.edu.unochapeco.feirinha.mapper.TransactionPostMapper;
import br.edu.unochapeco.feirinha.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "Transactions", description = "Group of endpoints responsible for dealing with Transactions operations")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionPostMapper transactionPostMapper;

    private final TransactionGetMapper transactionGetMapper;

    public TransactionController(
            TransactionService transactionService,
            TransactionPostMapper transactionPostMapper,
            TransactionGetMapper transactionGetMapper
    ) {
        this.transactionService = transactionService;
        this.transactionPostMapper = transactionPostMapper;
        this.transactionGetMapper = transactionGetMapper;
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = TransactionPostDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransactionGetDto> createTransaction(@RequestBody @Valid TransactionPostDto transactionPostDto)
            throws  PersonNotFoundException, ProductNotFoundException, TransactionTypeInvalidException,
                    InsuficientBalanceException, InsuficientInventoryException {

        var transaction = this.transactionPostMapper.toTransaction(transactionPostDto);

        var savedTransaction = this.transactionService.createTransaction(transaction);

        return new ResponseEntity<>(
                transactionGetMapper.toTransactionGetDto(savedTransaction),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all transactions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = TransactionGetDto.class)),
                            mediaType = "application/json"
                    )
            }),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TransactionGetDto>> getAllTransactions(
            @RequestParam(required = false)Date fromDate,
            @RequestParam(required = false)Date toDate,
            @RequestParam(required = false, defaultValue = "0")int pageNumber,
            @RequestParam(required = false, defaultValue = "25")int pageSize
    ) {
        if ((fromDate != null && toDate == null) || (fromDate == null && toDate != null)){
            throw new IllegalArgumentException("Não é possível informar apenas uma data, ou as duas ou nenhuma!");
        }

        if(fromDate != null && toDate != null){
            return new ResponseEntity<>(
                    transactionGetMapper.toPageTransactionGetDto(
                            this.transactionService.getAllTransactionsWithDateFilter(fromDate, toDate, pageNumber, pageSize)
                    ),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                transactionGetMapper.toPageTransactionGetDto(
                        this.transactionService.getAllTransactions(pageNumber, pageSize)
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get specific transaction by person id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TransactionGetDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(), mediaType = "application/json")
            }),
    })
    @GetMapping(value = "/person/{personId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TransactionGetDto>> getPersonTransactions(
            @PathVariable Long personId,
            @RequestParam(required = false)Date fromDate,
            @RequestParam(required = false)Date toDate,
            @RequestParam(required = false, defaultValue = "0")int pageNumber,
            @RequestParam(required = false, defaultValue = "25")int pageSize
    ) {
        if ((fromDate != null && toDate == null) || (fromDate == null && toDate != null)){
            throw new IllegalArgumentException("Não é possível informar apenas uma data, ou as duas ou nenhuma!");
        }

        if(fromDate != null && toDate != null){
            return new ResponseEntity<>(
                    transactionGetMapper.toPageTransactionGetDto(
                            this.transactionService.getAllPersonTransactionsWithDateFilter(personId, fromDate, toDate, pageNumber, pageSize)
                    ),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                transactionGetMapper.toPageTransactionGetDto(
                        this.transactionService.getAllPersonTransactions(personId, pageNumber, pageSize)
                ),
                HttpStatus.OK
        );
    }
}
