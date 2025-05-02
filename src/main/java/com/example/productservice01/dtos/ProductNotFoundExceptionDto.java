package com.example.productservice01.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductNotFoundExceptionDto {
    int errorCode;
    String message;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
