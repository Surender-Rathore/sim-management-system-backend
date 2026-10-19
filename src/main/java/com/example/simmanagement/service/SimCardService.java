package com.example.simmanagement.service;

import com.example.simmanagement.dto.SimCardRequest;
import com.example.simmanagement.dto.SimCardResponse;

import java.util.List;

public interface SimCardService {

    SimCardResponse create(SimCardRequest request);

    SimCardResponse getById(Long id);

    List<SimCardResponse> getAll();

    SimCardResponse update(Long id, SimCardRequest request);

    void delete(Long id);

    List<SimCardResponse> searchByMsisdn(String msisdn);
}