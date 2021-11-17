package com.auth.identity.repo;

import com.auth.identity.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo extends MongoRepository<Role, String>
{

    public Role findByName(String name);
}
