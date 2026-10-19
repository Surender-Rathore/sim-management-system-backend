package com.example.simmanagement.dto;

import java.time.LocalDate;

import com.example.simmanagement.domain.SimStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload to create or update a SIM card")
public record SimCardRequest(

        @Schema(description = "Integrated Circuit Card Identifier (unique)", example = "8949012345678901234")
        @NotBlank(message = "iccid is required")
        @Pattern(regexp = "\\d{19,22}", message = "iccid must be 19-22 digits")
        String iccid,

        @Schema(description = "CNIC (National Identity Card number) of the SIM owner", example = "42101-1234567-1")
        @NotBlank(message = "cnic is required")
        String cnic,

        @Schema(description = "Mobile number attached to the SIM (unique)", example = "4915112345678")
        @NotBlank(message = "msisdn is required")
        @Pattern(regexp = "\\d{7,15}", message = "msisdn must be 7-15 digits")
        String msisdn,

        @Schema(description = "Network carrier", example = "Telekom")
        @NotBlank(message = "carrier is required")
        @Size(max = 60)
        String carrier,

        @Schema(description = "Tariff / plan type", example = "Prepaid-S")
        @Size(max = 60)
        String planType,

        @Schema(description = "Lifecycle status. Defaults to INACTIVE when omitted.", example = "ACTIVE")
        SimStatus status,

        @Schema(description = "Owner or subscriber the SIM is assigned to", example = "Om Sarangani")
        @Size(max = 100)
        String assignedTo,

        @Schema(description = "Activation date (ISO-8601)", example = "2025-01-15")
        LocalDate activationDate
) {}