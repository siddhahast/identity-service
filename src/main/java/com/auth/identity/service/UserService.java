package com.auth.identity.service;

import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.Role;

import java.util.List;

public interface UserService
{

    public AppUser saveUser(AppUser user);

    public Role saveRole(Role role);

    public void addRoleToUser(String username, String roleName);

    public AppUser getUser(String username);

    public List<AppUser> getUsers();

    public AppUser signupUser(AppUser appUser);

}
