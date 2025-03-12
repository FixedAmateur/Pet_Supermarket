package com.laborexport.pet_supermarket.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(404, "User does not exist", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(401, "User is not authenticated", HttpStatus.UNAUTHORIZED),
    USER_LOGGED_OUT(401, "User is logged out", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(400, "Token expired", HttpStatus.BAD_REQUEST),
    USERNAME_CAN_NOT_BE_CHANGED(400, "User name can not be changed", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST)
    ;


    private int code;
    private String message;
    private HttpStatus status;
}
