package com.example.simmanagement.repository;

import com.example.simmanagement.domain.SimCard;
import com.example.simmanagement.domain.SimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

    boolean existsByIccid(String iccid);

    boolean existsByMsisdn(String msisdn);

    List<SimCard> findByStatus(SimStatus status);

    List<SimCard> findByMsisdnContaining(String msisdn);

    long countByCnic(String cnic);
}
