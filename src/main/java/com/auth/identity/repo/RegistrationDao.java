package com.auth.identity.repo;

import com.auth.identity.domain.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationDao extends JpaRepository<RegistrationRequest, Long>
{

    public RegistrationRequest findByEmail(String email);

    public RegistrationRequest findByPhone(String phone);

    public RegistrationRequest findByUsername(String username);

}
