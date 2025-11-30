package com.off3dstudio.off3dbackend.exception;

public class BadRequestException extends ApplicationException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

}
