package com.volvo.emspdemo.domain;

import com.google.common.collect.Lists;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Component
public final class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String contractId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Card> cards;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Account() {
    }

    protected Account(Long id, String email, AccountStatus status, String contractId, List<Card> cards, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.contractId = contractId;
        this.cards = cards;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected Account(Long id, String email, AccountStatus status, String contractId) {
        this(id, email, status, contractId, Lists.newArrayList(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Account createNew(AccountCreatedEvent event) {
        if(StringUtils.isBlank(event.getContractId())) {
            EmaId emaid = EmaId.creatNewFor("cn", "tjs");
            event.setContractId(emaid.toContractId());
        }
        return new Account(null, event.getEmail(), AccountStatus.CREATED, event.getContractId());
    }

    public Account updateStatus(AccountStatus newStatus) {
        this.status = newStatus;
        return this;
    }

    public Account updateContractId(String newContractId) {
        this.contractId = newContractId;
        return this;
    }

    public Account addCard(Card newCard) {
        //Hibernate.initialize(this.getCards());
        if(null == this.getCards()) {
            this.cards = new ArrayList<>();
        }
        this.cards.add(newCard);
        return this;
    }

}
