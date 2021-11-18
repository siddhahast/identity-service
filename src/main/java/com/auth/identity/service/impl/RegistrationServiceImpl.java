package com.auth.identity.service.impl;

import com.auth.identity.domain.JwtTokenRequest;
import com.auth.identity.domain.RegistrationRequest;
import com.auth.identity.excpetion.IdentityException;
import com.auth.identity.repo.RegistrationDao;
import com.auth.identity.service.RegistrationService;
import com.auth.identity.service.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RegistrationServiceImpl implements RegistrationService
{

    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private KafkaTemplate appUserKafkaTemplate;

    @Value("${authentication.secret}")
    private String tokenSecret;

    @Override
    public RegistrationRequest save(RegistrationRequest registrationRequest)
    {
        RegistrationRequest registration = registrationDao.save(registrationRequest);
        return registration;
    }

    @Override
    public RegistrationRequest register(RegistrationRequest registrationRequest)
    {
        RegistrationRequest registration = registrationDao.findByEmail(registrationRequest.getEmail());
        if(registration!=null)
        {
            throw new IdentityException("", null);
        }
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest.JwtTokenRequestBuilder()
                .username(registrationRequest.getUsername())
                .secretToken(tokenSecret)
                .roles(null)
                .build();

        String registrationToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        registrationRequest.setRegistrationToken(registrationToken);
        // TODO : Send this token to the email
        appUserKafkaTemplate.send("user-registration-request", registrationRequest);
        return registration;
    }

    @Override
    public RegistrationRequest retriggerRegistration(RegistrationRequest registrationRequest)
    {
        RegistrationRequest registration = registrationDao.findByUsername(registrationRequest.getUsername());
        if(registration == null)
        {
            // TODO : throw an exception
        }
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest.JwtTokenRequestBuilder()
                .username(registration.getUsername())
                .secretToken(tokenSecret)
                .roles(null)
                .build();
        String registrationToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        return registration;
    }

    @Override
    public void delete(RegistrationRequest registrationRequest)
    {

    }

    @Override
    public RegistrationRequest find(String userName)
    {
        return registrationDao.findByUsername(userName);
    }
}
