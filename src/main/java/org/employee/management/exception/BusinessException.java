package org.employee.management.exception;


import org.apache.coyote.BadRequestException;

public class BusinessException extends BadRequestException {
    public BusinessException(String message) {
        super(message);
    }
}
