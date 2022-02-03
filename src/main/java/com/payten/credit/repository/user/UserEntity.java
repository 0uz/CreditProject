package com.payten.credit.repository.user;

import com.payten.credit.repository.common.BaseEntity;
import com.payten.credit.repository.common.Status;
import com.payten.credit.repository.credit.CreditEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@Where(clause = "status <> 'DELETED'")
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private Long identificationNo;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private Double monthlyIncome;

    @Column(nullable = false)
    private Integer creditScore;


    private String password;


    @OneToMany(mappedBy = "user")
    private List<CreditEntity> credits;


    public UserEntity setModel(UserEntity existingUser) {
        UserEntity user =  new UserEntity();
        user.setId(existingUser.getId());
        user.setName(existingUser.getName());
        user.setSurname(existingUser.getSurname());
        user.setIdentificationNo(existingUser.getIdentificationNo());
        user.setPhoneNo(existingUser.getPhoneNo());
        user.setMonthlyIncome(existingUser.getMonthlyIncome());
        user.setCreditScore(existingUser.getCreditScore());
        return user;
    }
}
