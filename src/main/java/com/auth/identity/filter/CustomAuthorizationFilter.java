package com.auth.identity.filter;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.JwtTokenRequest;
import com.auth.identity.service.TokenUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.auth.identity.constants.AppConstants.JWT_TOKEN_ROLES_KEY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class CustomAuthorizationFilter extends OncePerRequestFilter
{

    @Value("${authentication.secret}")
    private String tokenSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {

        if(request.getServletPath().equalsIgnoreCase(AppConstants.API_LOGIN_URL) || request.getServletPath().equalsIgnoreCase(AppConstants.API_TOKEN_REFRESH)
         || request.getServletPath().equalsIgnoreCase(AppConstants.API_REGISTER))
        {
            filterChain.doFilter(request, response);
        }
        else
        {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader!=null && authorizationHeader.startsWith(AppConstants.JWT_TOKEN_PREFIX))
            {
                try
                {
                    JwtTokenRequest jwtTokenRequest = new JwtTokenRequest
                            .JwtTokenRequestBuilder()
                            .secretToken(tokenSecret)
                            .authorizationHeader(request.getHeader(AUTHORIZATION)).build();

                    DecodedJWT decodedJWT = TokenUtil.decodeJWToken(jwtTokenRequest);

                    String username = decodedJWT.getSubject();

                    String[] roles = decodedJWT.getClaim(JWT_TOKEN_ROLES_KEY).asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    for (String role : roles)
                    {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    filterChain.doFilter(request, response);
                }
                catch (Exception ex)
                {
                    response.setHeader("error", ex.getMessage());

                    response.sendError(HttpStatus.FORBIDDEN.value());
                }

            }
            else
            {
                filterChain.doFilter(request, response);
            }
        }

    }
}
