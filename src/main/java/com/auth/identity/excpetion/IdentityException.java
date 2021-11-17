package com.auth.identity.excpetion;

public class IdentityException extends RuntimeException
{

    private String errorCode;
    private String errorMessage;

    public IdentityException(String errorCode, String errorMessage)
    {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
