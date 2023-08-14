package com.rental.exception;

public class AuthenticatedSessionAlreadyExistsException extends Exception
{
    public AuthenticatedSessionAlreadyExistsException(String message)
    {
        super(message);
    }
}
