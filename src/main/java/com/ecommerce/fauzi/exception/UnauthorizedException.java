package com.ecommerce.fauzi.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatus(){
        return HttpStatus.UNAUTHORIZED;
    }
}
