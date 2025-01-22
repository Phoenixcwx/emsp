package com.volvo.emspdemo.domain.event;

import com.volvo.emspdemo.domain.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardStatusChangedEvent {
    private String rfId;
    private CardStatus status;
}
