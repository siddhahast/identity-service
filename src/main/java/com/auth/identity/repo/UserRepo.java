package com.auth.identity.repo;

import com.auth.identity.domain.AppUser;

import java.util.List;

public interface UserRepo
{

    public AppUser findByUsername(String username);

    public AppUser findByEmail(String email);

    public AppUser save(AppUser appUser);

    public List<AppUser> findAll();

}
