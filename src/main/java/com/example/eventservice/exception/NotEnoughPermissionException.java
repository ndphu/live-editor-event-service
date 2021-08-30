package com.example.eventservice.exception;

import org.springframework.security.core.AuthenticationException;


public class NotEnoughPermissionException extends AuthenticationException {
    public NotEnoughPermissionException(String msg) {
        super(msg);
    }
}
