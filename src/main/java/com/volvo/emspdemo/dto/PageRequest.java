package com.volvo.emspdemo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageRequest {
    private int pageNumber;
    private int pageSize;
    private LocalDateTime updateTime;
}
