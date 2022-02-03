package com.payten.credit.repository.credit;

import com.payten.credit.repository.common.BaseEntity;
import com.payten.credit.repository.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table
@Where(clause = "status <> 'DELETED'")
public class CreditEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @Column(nullable = false)
    private Double creditLimit;

}
