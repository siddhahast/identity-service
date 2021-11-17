package com.auth.identity.repo;

import com.auth.identity.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long>
{

    public AppUser findByUsername(String username);

    public AppUser findByEmail(String email);

}
