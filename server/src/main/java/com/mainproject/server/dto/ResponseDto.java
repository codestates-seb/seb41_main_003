package com.mainproject.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private Object data;

    public static ResponseDto of(Object data) {
        return new ResponseDto(data);
    }
}
