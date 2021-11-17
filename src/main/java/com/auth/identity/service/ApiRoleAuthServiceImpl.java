package com.auth.identity.service;

import com.auth.identity.domain.ApiRoleAuth;
import com.auth.identity.repo.ApiRoleAuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiRoleAuthServiceImpl implements ApiRoleAuthService
{

    @Autowired
    private ApiRoleAuthRepo apiRoleAuthRepo;

    @Override
    public List<ApiRoleAuth> findAll()
    {
        return apiRoleAuthRepo.findAll();
    }

    @Override
    public void save(ApiRoleAuth apiRoleAuth) {
        apiRoleAuthRepo.save(apiRoleAuth);
    }
}
