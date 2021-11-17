package com.auth.identity.domain;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;

import java.util.List;

@Data
public class JwtTokenRequest
{

    private String username;
    private List<String> roles;
    private Long timeLimitForExpiry;
    private String urlIssuer;
    private Algorithm algorithm;
    private String authorizationHeader;
    private String tokenSecret;
    private Long timeElapse;

    public JwtTokenRequest(JwtTokenRequestBuilder jwtTokenRequestBuilder)
    {
        this.username = jwtTokenRequestBuilder.username;
        this.roles = jwtTokenRequestBuilder.roles;
        this.timeLimitForExpiry = jwtTokenRequestBuilder.timeLimitForExpiry;
        this.urlIssuer = jwtTokenRequestBuilder.urlIssuer;
        this.algorithm = jwtTokenRequestBuilder.algorithm;
        this.authorizationHeader = jwtTokenRequestBuilder.authorizationHeader;
        this.tokenSecret = jwtTokenRequestBuilder.tokenSecret;
    }

    public static class JwtTokenRequestBuilder{

        private String username;
        private List<String> roles;
        private String urlIssuer;
        private Long timeLimitForExpiry;
        private Algorithm algorithm;
        private String authorizationHeader;
        private String tokenSecret;

        public JwtTokenRequestBuilder()
        {

        }

        public JwtTokenRequestBuilder username(String username)
        {
            this.username = username;
            return this;
        }

        public JwtTokenRequestBuilder roles(List<String> roles)
        {
            this.roles = roles;
            return this;
        }

        public JwtTokenRequestBuilder urlIssuer(String urlIssuer)
        {
            this.urlIssuer = urlIssuer;
            return this;
        }

        public JwtTokenRequestBuilder timeLimitForExpiry(Long timeLimitForExpiry)
        {
            this.timeLimitForExpiry = timeLimitForExpiry;
            return this;
        }

        public JwtTokenRequestBuilder algorithm(Algorithm algorithm)
        {
            this.algorithm = algorithm;
            return this;
        }

        public JwtTokenRequestBuilder authorizationHeader(String authorizationHeader)
        {
            this.authorizationHeader = authorizationHeader;
            return this;
        }

        public JwtTokenRequestBuilder secretToken(String tokenSecret)
        {
            this.tokenSecret = tokenSecret;
            return this;
        }

        public JwtTokenRequest build()
        {
            JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(this);
            return jwtTokenRequest;
        }
    }

}
