package com.auth.identity.service;

import com.auth.identity.domain.RegistrationRequest;

public interface RegistrationService
{

    public RegistrationRequest save(RegistrationRequest registrationRequest);

    public RegistrationRequest register(RegistrationRequest registrationRequest);

    public RegistrationRequest retriggerRegistration(RegistrationRequest registrationRequest);

    public void delete(RegistrationRequest registrationRequest);

    public RegistrationRequest find(String userName);

}
