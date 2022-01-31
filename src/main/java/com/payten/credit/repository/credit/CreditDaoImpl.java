package com.payten.credit.repository.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreditDaoImpl implements CreditDao{

    private final CreditJpaRepository creditJpaRepository;

    @Override
    public void save(CreditEntity credit) {
        creditJpaRepository.save(credit);
    }
}
