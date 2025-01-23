package com.volvo.emspdemo.domain;

import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
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
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Aggregate
@Getter
@Setter
@EnableJpaAuditing
public class Account {
    @Id
    @AggregateIdentifier
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String contractId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Account() {
    }

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getEmail(), command.getContractId()));
    }

    @CommandHandler
    public void handle(ChangeAccountStatusCommand command) {
        apply(new AccountStatusChangedEvent(command.getAccountId(), command.getStatus()));
    }
}
