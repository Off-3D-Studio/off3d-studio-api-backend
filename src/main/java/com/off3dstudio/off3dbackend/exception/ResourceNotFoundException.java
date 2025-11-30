package com.off3dstudio.off3dbackend.exception;

public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
