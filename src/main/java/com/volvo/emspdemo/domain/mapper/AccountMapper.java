package com.volvo.emspdemo.domain.mapper;

import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.dto.AssignCardToAccountRequest;
import com.volvo.emspdemo.dto.ChangeAccountStatusRequest;
import com.volvo.emspdemo.dto.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountCreatedEvent fromRequest(CreateAccountRequest request);
    AccountStatusChangedEvent fromRequest(ChangeAccountStatusRequest request);
    CardAssignedToAccountEvent fromRequest(AssignCardToAccountRequest request);
}
