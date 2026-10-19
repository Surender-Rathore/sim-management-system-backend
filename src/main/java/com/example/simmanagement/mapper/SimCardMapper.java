package com.example.simmanagement.mapper;

import org.springframework.stereotype.Component;

import com.example.simmanagement.domain.SimCard;
import com.example.simmanagement.dto.SimCardRequest;
import com.example.simmanagement.dto.SimCardResponse;

@Component
public class SimCardMapper {

    public SimCard toEntity(SimCardRequest req) {
        SimCard sim = new SimCard();
        apply(sim, req);
        return sim;
    }

    /** Copies request fields onto an existing entity (used for create & update). */
    public void apply(SimCard sim, SimCardRequest req) {
        sim.setIccid(req.iccid());
        sim.setCnic(req.cnic());
        sim.setMsisdn(req.msisdn());
        sim.setCarrier(req.carrier());
        sim.setPlanType(req.planType());
        sim.setStatus(req.status());
        sim.setAssignedTo(req.assignedTo());
        sim.setActivationDate(req.activationDate());
    }

    public SimCardResponse toResponse(SimCard sim) {
        return new SimCardResponse(
                sim.getId(),
                sim.getIccid(),
                sim.getCnic(),
                sim.getMsisdn(),
                sim.getCarrier(),
                sim.getPlanType(),
                sim.getStatus(),
                sim.getAssignedTo(),
                sim.getActivationDate(),
                sim.getCreatedAt(),
                sim.getUpdatedAt()
        );
    }
}