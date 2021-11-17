package com.auth.identity.repo;

import com.auth.identity.domain.RegistrationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationDao extends MongoRepository<RegistrationRequest, String>
{

    public RegistrationRequest findByEmail(String email);

    public RegistrationRequest findByPhone(String phone);

    public RegistrationRequest findByUsername(String username);

}
