package com.volvo.emspdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private int pageNumber;
    private int pageSize;
    private LocalDateTime updateTime;
}
