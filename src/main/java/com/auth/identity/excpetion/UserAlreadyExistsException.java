package com.auth.identity.excpetion;

public class UserAlreadyExistsException extends IdentityException
{
    public UserAlreadyExistsException(String errorCode, String errorMessage)
    {
        super(errorCode, errorMessage);
    }
}
