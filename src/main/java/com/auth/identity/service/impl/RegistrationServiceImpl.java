package com.auth.identity.service.impl;

import com.auth.identity.domain.JwtTokenRequest;
import com.auth.identity.domain.RegistrationRequest;
import com.auth.identity.excpetion.IdentityException;
import com.auth.identity.excpetion.UserAlreadyExistsException;
import com.auth.identity.repo.RegistrationDao;
import com.auth.identity.service.RegistrationService;
import com.auth.identity.service.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.auth.identity.excpetion.IdentityError.UNKNOWN_REGISTRATION_RETRIGGER_ATTEMPT;
import static com.auth.identity.excpetion.IdentityError.USER_ALREADY_EXIST;

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
            throw new UserAlreadyExistsException(USER_ALREADY_EXIST.name(), "User with email : " + registrationRequest.getEmail() + " already exists");
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
            throw new IdentityException(UNKNOWN_REGISTRATION_RETRIGGER_ATTEMPT.name(), "User is not registered.");
        }
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest.JwtTokenRequestBuilder()
                .username(registration.getUsername())
                .secretToken(tokenSecret)
                .roles(null)
                .build();
        String registrationToken = TokenUtil.prepareJWToken(jwtTokenRequest);
        // TODO : Retrigger email with registration token to verify
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
