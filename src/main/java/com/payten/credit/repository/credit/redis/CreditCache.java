package com.payten.credit.repository.credit.redis;

import com.payten.credit.service.credit.Credit;

import java.util.Optional;

public interface CreditCache {
    Optional<Credit> retrieveCredit(Long creditId);
    void createCredit(Credit credit, Long identificationNo);
}
