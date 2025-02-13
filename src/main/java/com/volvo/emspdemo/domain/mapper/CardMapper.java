package com.volvo.emspdemo.domain.mapper;

import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.dto.ChangeCardStatusRequest;
import com.volvo.emspdemo.dto.CreateCardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);
    CardCreatedEvent fromRequest(CreateCardRequest request);
    CardStatusChangedEvent fromRequest(ChangeCardStatusRequest request);
}
