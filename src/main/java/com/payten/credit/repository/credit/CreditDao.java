package com.payten.credit.repository.credit;

import java.util.Optional;

public interface CreditDao {
    void save(CreditEntity credit);
    Optional<CreditEntity> retrieveByIdentificationNo(Long identificationNo);

}
