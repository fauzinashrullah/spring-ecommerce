package com.ecommerce.fauzi.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends BusinessException {
    public AlreadyExistException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatus(){
        return HttpStatus.CONFLICT;
    }
}
