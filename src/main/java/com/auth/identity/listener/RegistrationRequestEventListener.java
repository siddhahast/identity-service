package com.auth.identity.listener;

import com.auth.identity.domain.RegistrationRequest;
import com.auth.identity.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestEventListener
{
    @Autowired
    private RegistrationService registrationService;

    @KafkaListener(groupId = "registrationuser-1", topics = "user-registration-request", containerFactory = "appUserKafkaListenerContainerFactory")
    public void processRegistrationRequest(RegistrationRequest registrationRequest)
    {
        RegistrationRequest registeredUser = registrationService.find(registrationRequest.getUsername());
        if(registeredUser!=null)
        {
            // log this as an already registered user
        }
        else
        {
            RegistrationRequest registration = registrationService.save(registrationRequest);
        }
    }
}
