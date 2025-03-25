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
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST),

    ONLY_ONE_DISCOUNT_TYPE_PERMITTED(400, "Discount cannot be in percentage and number at  once!", HttpStatus.BAD_REQUEST),

    PRODUCT_NAME_AND_TYPE_ALREADY_EXIST(400, "product name and type already exists!", HttpStatus.BAD_REQUEST),
    CANNOT_ALTER_NOT_EXISTED_PRODUCT(400, "updation cannot be made to inexisted product", HttpStatus.BAD_REQUEST),

    ORDER_HAS_BEEN_PURCHASED(400, "Cannot make purchase for the cart which was previously paid", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER_OF_CARTS(400, "Only 1 cart per person allowed", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER_OF_CART_PRODUCTS(400, "Only 1 product id per person within his cart allowed", HttpStatus.BAD_REQUEST),

    NOT_EXIST_ANY_IMAGE_WITHIN_PET(404, "Pet doesn't have any image with the given image id", HttpStatus.NOT_FOUND),
    CANNOT_REMOVE_DEFAULT_IMAGE(400, "cannot remove default image", HttpStatus.BAD_REQUEST)

    ;


    private int code;
    private String message;
    private HttpStatus status;
}
