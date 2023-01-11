package com.mainproject.server.utils;

import com.google.gson.Gson;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(
            HttpServletResponse response,
            Object status) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        if (status instanceof HttpStatus) {
            errorResponse = ErrorResponse.of((HttpStatus) status);
        } else if (status instanceof ErrorCode) {
            errorResponse = ErrorResponse.of((ErrorCode) status);
        }

        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.valueOf(errorResponse.getStatus()).value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

}