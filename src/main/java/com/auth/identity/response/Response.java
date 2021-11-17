package com.auth.identity.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<O> implements Serializable
{

    @JsonProperty("httpCode")
    private Integer httpCode;
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("body")
    private O body;
    @JsonProperty("error")
    private Error error;

}
