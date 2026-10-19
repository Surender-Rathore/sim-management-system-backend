package com.example.simmanagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.simmanagement.dto.SimCardRequest;
import com.example.simmanagement.dto.SimCardResponse;
import com.example.simmanagement.service.SimCardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sim-cards")
@Tag(name = "SIM Cards", description = "Create, read, update and delete SIM cards")
public class SimCardController {

    private final SimCardService service;

    public SimCardController(SimCardService service) {
        this.service = service;
    }

    @Operation(summary = "Create a SIM card",
            description = "Registers a new SIM card. ICCID and MSISDN must be unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "SIM card created"),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate ICCID or MSISDN",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<SimCardResponse> create(@Valid @RequestBody SimCardRequest request,
                                                  UriComponentsBuilder uriBuilder) {
        SimCardResponse created = service.create(request);
        URI location = uriBuilder.path("/api/v1/sim-cards/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Get a SIM card by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SIM card found"),
            @ApiResponse(responseCode = "404", description = "SIM card not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SimCardResponse> getById(
            @Parameter(description = "SIM card id", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "List all SIM cards")
    @ApiResponse(responseCode = "200", description = "List of SIM cards")
    @GetMapping
    public ResponseEntity<List<SimCardResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Search SIM cards by phone number",
            description = "Returns SIM cards whose MSISDN contains the given value (partial match).")
    @ApiResponse(responseCode = "200", description = "List of matching SIM cards")
    @GetMapping("/search")
    public ResponseEntity<List<SimCardResponse>> searchByMsisdn(
            @Parameter(description = "Full or partial phone number", example = "0300")
            @RequestParam String msisdn) {
        return ResponseEntity.ok(service.searchByMsisdn(msisdn));
    }

    @Operation(summary = "Update a SIM card",
            description = "Replaces all mutable fields of an existing SIM card.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SIM card updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "SIM card not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate ICCID or MSISDN",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SimCardResponse> update(
            @Parameter(description = "SIM card id", example = "1") @PathVariable Long id,
            @Valid @RequestBody SimCardRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a SIM card")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "SIM card deleted"),
            @ApiResponse(responseCode = "404", description = "SIM card not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "SIM card id", example = "1") @PathVariable Long id) {
        service.delete(id);
    }
}