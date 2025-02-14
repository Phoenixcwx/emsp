package com.volvo.emspdemo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Entity
@Getter
public final class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rfId;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    protected Card() {
    }

    public static Card createNew(String rfId, CardStatus status) {
        Card card =  new Card();
        card.rfId = rfId;
        card.status = status;
        card.createdAt = LocalDateTime.now();
        card.updatedAt = LocalDateTime.now();
        return card;
    }


    public Card assignCardToAccount(Account account) {
        this.account = account;
        this.status = CardStatus.ASSIGNED;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public Card changeState(CardStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
        return this;
    }
}
