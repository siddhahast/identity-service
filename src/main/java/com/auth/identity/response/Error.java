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
public class Error implements Serializable
{
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
}
