package com.auth.identity.repo;

import com.auth.identity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long>
{

    public Role findByName(String name);
}
