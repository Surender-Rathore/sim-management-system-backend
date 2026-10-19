package com.example.simmanagement.service;

import com.example.simmanagement.domain.SimCard;
import com.example.simmanagement.dto.SimCardRequest;
import com.example.simmanagement.dto.SimCardResponse;
import com.example.simmanagement.exception.DuplicateResourceException;
import com.example.simmanagement.exception.ResourceNotFoundException;
import com.example.simmanagement.exception.SimLimitExceededException;
import com.example.simmanagement.mapper.SimCardMapper;
import com.example.simmanagement.repository.SimCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SimCardServiceImpl implements SimCardService {

    private final SimCardRepository repository;
    private final SimCardMapper mapper;
    private static final int MAX_SIMS_PER_CNIC = 5;

    public SimCardServiceImpl(SimCardRepository repository, SimCardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


@Override
public SimCardResponse create(SimCardRequest request) {
    if (repository.existsByIccid(request.iccid())) {
        throw new DuplicateResourceException("SIM with iccid " + request.iccid() + " already exists");
    }
    if (repository.existsByMsisdn(request.msisdn())) {
        throw new DuplicateResourceException("SIM with msisdn " + request.msisdn() + " already exists");
    }

    long existingCount = repository.countByCnic(request.cnic());
    if (existingCount >= MAX_SIMS_PER_CNIC) {
        throw new SimLimitExceededException(
            "CNIC " + request.cnic() + " already has " + existingCount
            + " SIMs. Maximum allowed is " + MAX_SIMS_PER_CNIC);
    }

    SimCard saved = repository.save(mapper.toEntity(request));
    return mapper.toResponse(saved);
}

    @Override
    @Transactional(readOnly = true)
    public SimCardResponse getById(Long id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimCardResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimCardResponse> searchByMsisdn(String msisdn) {
        return repository.findByMsisdnContaining(msisdn).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SimCardResponse update(Long id, SimCardRequest request) {
        SimCard sim = findOrThrow(id);

        if (!sim.getIccid().equals(request.iccid()) && repository.existsByIccid(request.iccid())) {
            throw new DuplicateResourceException("SIM with iccid " + request.iccid() + " already exists");
        }
        if (!sim.getMsisdn().equals(request.msisdn()) && repository.existsByMsisdn(request.msisdn())) {
            throw new DuplicateResourceException("SIM with msisdn " + request.msisdn() + " already exists");
        }

        mapper.apply(sim, request);
        return mapper.toResponse(repository.save(sim));
    }

    @Override
    public void delete(Long id) {
        SimCard sim = findOrThrow(id);
        repository.delete(sim);
    }

    private SimCard findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SIM card not found with id " + id));
    }
}