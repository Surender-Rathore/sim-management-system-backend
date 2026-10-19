package com.example.simmanagement.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(
    name = "sim_cards",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_sim_iccid", columnNames = "iccid"),
        @UniqueConstraint(name = "uk_sim_msisdn", columnNames = "msisdn")
    }
)
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Integrated Circuit Card Identifier (unique per SIM). */
    @Column(nullable = false, length = 22)
    private String iccid;

    /** CNIC (National Identity Card number) of the SIM owner. */
    @Column(nullable = false, length = 15)
    private String cnic;

    /** Mobile number (phone number) attached to the SIM. */
    @Column(nullable = false, length = 15)
    private String msisdn;

    @Column(nullable = false, length = 60)
    private String carrier;

    @Column(length = 60)
    private String planType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SimStatus status;

    @Column(length = 100)
    private String assignedTo;

    private LocalDate activationDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = SimStatus.INACTIVE;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // --- Getters & setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIccid() { return iccid; }
    public void setIccid(String iccid) { this.iccid = iccid; }

    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getMsisdn() { return msisdn; }
    public void setMsisdn(String msisdn) { this.msisdn = msisdn; }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }

    public SimStatus getStatus() { return status; }
    public void setStatus(SimStatus status) { this.status = status; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public LocalDate getActivationDate() { return activationDate; }
    public void setActivationDate(LocalDate activationDate) { this.activationDate = activationDate; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}