package com.volvo.emspdemo.dto;

import lombok.Data;

@Data
public class PageRequest {
    private int pageNumber;
    private int pageSize;
}
