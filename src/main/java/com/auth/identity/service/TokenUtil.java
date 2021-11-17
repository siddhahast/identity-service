package com.auth.identity.service;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.JwtTokenRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;

public class TokenUtil
{

    public static String prepareJWToken(JwtTokenRequest jwtTokenRequest)
    {
        String subject = jwtTokenRequest.getUsername();
        List<String> authorities = jwtTokenRequest.getRoles();

        String accessToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                .withIssuer(jwtTokenRequest.getUrlIssuer())
                .withClaim("roles", authorities)
                .sign(Algorithm.HMAC256(jwtTokenRequest.getTokenSecret()));

        return accessToken;

    }

    public static String prepareRefreshToken(JwtTokenRequest jwtTokenRequest)
    {

        String refreshToken = JWT.create().withSubject(jwtTokenRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 60*60*1000))
                .withIssuer(jwtTokenRequest.getUrlIssuer())
                .sign(Algorithm.HMAC256(jwtTokenRequest.getTokenSecret()));

        return refreshToken;
    }

    public static DecodedJWT decodeJWToken(JwtTokenRequest jwtTokenRequest)
    {
        String authorizationHeader = jwtTokenRequest.getAuthorizationHeader();
        String token = authorizationHeader.substring(AppConstants.JWT_TOKEN_PREFIX.length());
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtTokenRequest.getTokenSecret())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT;
    }
}
