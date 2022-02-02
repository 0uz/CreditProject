package com.payten.credit.repository.user;

import com.payten.credit.repository.credit.CreditEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user")
    private List<CreditEntity> credits;


    public UserEntity setModel(UserEntity existingUser) {
        UserEntity user =  new UserEntity();
        user.setName(existingUser.getName());
        user.setSurname(existingUser.getSurname());
        user.setIdentificationNo(existingUser.getIdentificationNo());
        user.setPhoneNo(existingUser.getPhoneNo());
        user.setMonthlyIncome(existingUser.getMonthlyIncome());
        return user;
    }
}
