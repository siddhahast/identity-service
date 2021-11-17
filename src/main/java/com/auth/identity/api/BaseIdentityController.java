package com.auth.identity.api;

import com.auth.identity.domain.AppUser;
import com.auth.identity.excpetion.IdentityException;
import com.auth.identity.response.Response;
import com.auth.identity.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseIdentityController
{

    @Autowired
    protected ResponseBuilder responseBuilder;

    @ExceptionHandler(value = IdentityException.class)
    public Response<AppUser> handleError(IdentityException exception)
    {
        return responseBuilder.buildErrorResponse(exception);
    }

}
