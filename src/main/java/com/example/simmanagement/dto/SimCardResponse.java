package com.example.simmanagement.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.example.simmanagement.domain.SimStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SIM card representation returned by the API")
public record SimCardResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "8949012345678901234") String iccid,
        @Schema(example = "42101-1234567-1") String cnic,
        @Schema(example = "4915112345678") String msisdn,
        @Schema(example = "Telekom") String carrier,
        @Schema(example = "Prepaid-S") String planType,
        @Schema(example = "ACTIVE") SimStatus status,
        @Schema(example = "Om Sarangani") String assignedTo,
        @Schema(example = "2025-01-15") LocalDate activationDate,
        Instant createdAt,
        Instant updatedAt
) {}