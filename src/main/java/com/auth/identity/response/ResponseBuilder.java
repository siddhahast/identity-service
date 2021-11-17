package com.auth.identity.response;

import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder<O>
{

    public Response<O> buildErrorResponse(Exception exception)
    {
        Response<O> response = new Response<>();
        Error error = new Error();
        error.setMessage(exception.getMessage());
        response.setError(error);
        return response;
    }

    public Response<O> buildSuccess(O object)
    {
        Response<O> response = new Response<>();
        response.setBody(object);
        response.setHttpCode(200);
        response.setError(null);
        response.setResponseCode("ID_2000");
        return response;
    }


}
