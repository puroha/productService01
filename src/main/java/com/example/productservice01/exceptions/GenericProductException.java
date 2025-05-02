package com.example.productservice01.exceptions;

public class GenericProductException extends Exception {
    private int errorCode;
    private String message;

    public GenericProductException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
