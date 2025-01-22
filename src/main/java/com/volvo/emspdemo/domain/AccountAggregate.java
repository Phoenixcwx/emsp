package com.volvo.emspdemo.domain;

import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.validator.constraints.Email;

import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Aggregate
@Getter
public class AccountAggregate {
    @Id
    @AggregateIdentifier
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String contractId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardAggregate> cards;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getEmail(), command.getContractId()));
    }

    @CommandHandler
    public void handle(ChangeAccountStatusCommand command) {
        apply(new AccountStatusChangedEvent(command.getEmail(), command.getStatus()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.email = event.getEmail();
        this.contractId = event.getContractId();
        this.status = AccountStatus.ACTIVATED;
    }

    @EventSourcingHandler
    public void on(AccountStatusChangedEvent event) {
        this.status = event.getStatus();
    }
}
