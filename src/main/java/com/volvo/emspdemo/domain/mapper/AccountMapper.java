package com.volvo.emspdemo.domain.mapper;

import com.volvo.emspdemo.domain.command.AssignCardToAccountCommand;
import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.dto.AssignCardToAccountRequest;
import com.volvo.emspdemo.dto.ChangeAccountStatusRequest;
import com.volvo.emspdemo.dto.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    CreateAccountCommand fromRequest(CreateAccountRequest request);
    ChangeAccountStatusCommand fromRequest(ChangeAccountStatusRequest request);
    AssignCardToAccountCommand fromRequest(AssignCardToAccountRequest request);
}
