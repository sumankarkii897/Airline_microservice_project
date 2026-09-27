package com.himalayan.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiResponse<T> {
    private int statusCode;
    private Date timestamp;
    private String message;
    private T data;
}
