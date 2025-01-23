package com.volvo.emspdemo.domain.mapper;

import com.volvo.emspdemo.domain.command.ChangeCardStatusCommand;
import com.volvo.emspdemo.domain.command.CreateCardCommand;
import com.volvo.emspdemo.dto.ChangeCardStatusRequest;
import com.volvo.emspdemo.dto.CreateCardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);
    CreateCardCommand fromRequest(CreateCardRequest request);
    ChangeCardStatusCommand fromRequest(ChangeCardStatusRequest request);
}
