package com.payten.credit.repository.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreditDaoImpl implements CreditDao{

    private final CreditJpaRepository creditJpaRepository;

    @Override
    public void save(CreditEntity credit) {
        creditJpaRepository.save(credit);
    }

    @Override
    public Optional<CreditEntity> retrieveByIdentificationNo(Long identificationNo) {
        return creditJpaRepository.findCreditEntityByUser_IdentificationNo(identificationNo);
    }
}
