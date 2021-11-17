package com.auth.identity.service;

import com.auth.identity.domain.ApiRoleAuth;

import java.util.List;

public interface ApiRoleAuthService
{

    public List<ApiRoleAuth> findAll();

    public void save(ApiRoleAuth apiRoleAuth);

}
