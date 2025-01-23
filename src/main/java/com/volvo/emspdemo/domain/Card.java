package com.volvo.emspdemo.domain;

import com.volvo.emspdemo.domain.command.AssignCardToAccountCommand;
import com.volvo.emspdemo.domain.command.ChangeCardStatusCommand;
import com.volvo.emspdemo.domain.command.CreateCardCommand;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Data
@Aggregate
@Getter
public class Card {
    @Id
    @AggregateIdentifier
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rfId;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Card() {
    }

    @CommandHandler
    public Card(CreateCardCommand command) {
        apply(new CardCreatedEvent(command.getCardId(), command.getRfId(), command.getContractId()));
    }

    @CommandHandler
    public void handle(AssignCardToAccountCommand command) {
        apply(new CardAssignedToAccountEvent(command.getCardId(), command.getAccountId(), command.getContractId()));
    }

    @CommandHandler
    public void handle(ChangeCardStatusCommand command) {
        apply(new CardStatusChangedEvent(command.getCardId(), command.getStatus()));
    }
}
