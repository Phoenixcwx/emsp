package com.volvo.emspdemo.domain.command;

import com.volvo.emspdemo.domain.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCardStatusCommand {
    private String uid;
    private CardStatus status;
}
