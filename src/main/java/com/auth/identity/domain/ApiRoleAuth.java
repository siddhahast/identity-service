package com.auth.identity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoleAuth
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
