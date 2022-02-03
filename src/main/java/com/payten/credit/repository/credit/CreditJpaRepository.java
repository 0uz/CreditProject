package com.payten.credit.repository.credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditJpaRepository extends JpaRepository<CreditEntity, Long> {
    Optional<CreditEntity> findCreditEntityByUser_IdentificationNo(Long identificationNo);
}
