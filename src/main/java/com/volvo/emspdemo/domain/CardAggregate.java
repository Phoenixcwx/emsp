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
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Data
@Aggregate
@Getter
public class CardAggregate {
    @Id
    @AggregateIdentifier
    private String rfId;

    private String contractId;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @ManyToOne
    @JoinColumn(name = "email")
    private AccountAggregate account;

    public CardAggregate() {
    }

    @CommandHandler
    public CardAggregate(CreateCardCommand command) {
        apply(new CardCreatedEvent(command.getRfId(), command.getContractId()));
    }

    @CommandHandler
    public void handle(AssignCardToAccountCommand command) {
        apply(new CardAssignedToAccountEvent(command.getEmail(), command.getUid()));
    }

    @CommandHandler
    public void handle(ChangeCardStatusCommand command) {
        apply(new CardStatusChangedEvent(command.getUid(), command.getStatus()));
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent event) {
        this.rfId = event.getRfId();
        this.contractId = event.getContractId();
        this.status = CardStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(CardAssignedToAccountEvent event) {
        this.status = CardStatus.ASSIGNED;
    }

    @EventSourcingHandler
    public void on(CardStatusChangedEvent event) {
        this.status = event.getStatus();
    }
}
