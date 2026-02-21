package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
