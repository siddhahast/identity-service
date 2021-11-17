package com.auth.identity.repo;

import com.auth.identity.domain.ApiRoleAuth;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiRoleAuthRepo extends MongoRepository<ApiRoleAuth, Long>
{

}
