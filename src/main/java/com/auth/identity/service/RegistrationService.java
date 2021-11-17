package com.auth.identity.service;

import com.auth.identity.domain.RegistrationRequest;

public interface RegistrationService
{

    public RegistrationRequest save(RegistrationRequest registrationRequest);

    public RegistrationRequest retriggerRegistration(RegistrationRequest registrationRequest);

    public void delete(RegistrationRequest registrationRequest);

}
