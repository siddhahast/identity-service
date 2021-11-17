package com.auth.identity.repo;

import com.auth.identity.domain.ApiRoleAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRoleAuthRepo extends JpaRepository<ApiRoleAuth, Long>
{

}
