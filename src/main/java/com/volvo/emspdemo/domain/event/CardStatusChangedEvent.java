package com.volvo.emspdemo.domain.event;

import com.volvo.emspdemo.domain.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusChangedEvent {
    private Long cardId;
    private CardStatus status;
}
