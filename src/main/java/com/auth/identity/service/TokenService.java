package com.auth.identity.service;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.JwtTokenRequest;
import com.auth.identity.domain.RegistrationRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class TokenService
{

    @Autowired
    private UserService userService;

    @Value("${authentication.secret}")
    private String tokenSecret;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith(AppConstants.JWT_TOKEN_PREFIX))
        {
            try
            {
                String refreshToken = authorizationHeader.substring(AppConstants.JWT_TOKEN_PREFIX.length());

                JwtTokenRequest jwtTokenRequest = new JwtTokenRequest
                        .JwtTokenRequestBuilder()
                        .authorizationHeader(request.getHeader(AUTHORIZATION))
                        .urlIssuer(request.getRequestURL().toString())
                        .secretToken(tokenSecret)
                        .build();

                DecodedJWT decodedJWT = TokenUtil.decodeJWToken(jwtTokenRequest);

                String username = decodedJWT.getSubject();

                AppUser appUser = userService.getUser(username);

                if(appUser!=null)
                {
                    jwtTokenRequest.setUsername(appUser.getUsername());
                    jwtTokenRequest.setRoles(appUser.getRoles().stream().map(r->r.getName()).collect(Collectors.toList()));
                    String accessToken = TokenUtil.prepareJWToken(jwtTokenRequest);

                    Map<String, String> tokens = new HashMap<>();

                    tokens.put("access_token", accessToken);
                    tokens.put("refresh_token", refreshToken);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }
            }
            catch (Exception exception)
            {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }
        else
        {
            throw new RuntimeException("Refresh Token is missing");
        }
    }

    public AppUser refreshRegistrationToken(AppUser appUser)
    {
        AppUser user = userService.getUser(appUser.getUsername());
        if(user == null)
        {
            //TODO :
            /*
            1. Check the token expiry
            2. if not expired
             */
        }
        appUser.setUsername("registration:" + appUser.getUsername());
        String registrationRefreshToken = signupToken(appUser);
        // Send an email - event driven
        return null;
    }

    public String signupToken(AppUser appUser)
    {
        List<String> roles = appUser.getRoles().stream().map(r->r.getName()).collect(Collectors.toList());
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest.JwtTokenRequestBuilder()
                .username(appUser.getUsername())
                .roles(roles)
                .secretToken(tokenSecret)
                .urlIssuer("/api/registration")
                .build();
        String registrationRefreshToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        return registrationRefreshToken;
    }

    public String registrationToken(RegistrationRequest registrationRequest)
    {
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest.JwtTokenRequestBuilder()
                .username(registrationRequest.getUsername())
                .roles(null)
                .secretToken(tokenSecret)
                .urlIssuer("/api/registration")
                .build();
        String registrationRefreshToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        return registrationRefreshToken;
    }

}
