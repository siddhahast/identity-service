package com.auth.identity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoleAuth
{

    @Id
    private String id;
    private String requestMethod;
    private String urlPattern;
    private String role;

//    public ApiRoleAuth(Long id, String requestMethod, String urlPattern, String role)
//    {
//        this.id = id;
//        this.requestMethod = requestMethod;
//        this.urlPattern = urlPattern;
//        this.role = role;
//    }

}
