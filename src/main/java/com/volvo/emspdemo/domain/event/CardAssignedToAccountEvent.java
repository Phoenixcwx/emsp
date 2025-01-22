package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardAssignedToAccountEvent {
    private String email;
    private String rfId;
}
