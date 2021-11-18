package com.auth.identity.filter;

import com.auth.identity.domain.JwtTokenRequest;
import com.auth.identity.service.TokenUtil;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private AuthenticationManager authenticationManager;

    private String tokenSecret;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String tokenSecret)
    {
        this.authenticationManager = authenticationManager;
        this.tokenSecret = tokenSecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException
    {
        JwtTokenRequest jwtTokenRequest = prepareJwtTokenRequest(request, authentication);
        String accessToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        String refreshToken = TokenUtil.prepareRefreshToken(jwtTokenRequest);
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
    }

    private JwtTokenRequest prepareJwtTokenRequest(HttpServletRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest
                .JwtTokenRequestBuilder()
                .username(user.getUsername())
                .roles(roles)
                .urlIssuer(request.getRequestURL().toString())
                .secretToken(tokenSecret)
                .algorithm(Algorithm.HMAC256(tokenSecret))
                .authorizationHeader(request.getHeader(AUTHORIZATION)).build();
        return jwtTokenRequest;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
    {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
