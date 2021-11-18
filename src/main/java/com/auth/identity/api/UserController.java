package com.auth.identity.api;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.Role;
import com.auth.identity.domain.RoleToUser;
import com.auth.identity.response.Response;
import com.auth.identity.service.TokenService;
import com.auth.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class UserController extends BaseIdentityController
{

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenRefreshService;

    @PostMapping(name = AppConstants.API_SIGNUP)
    public ResponseEntity<AppUser> signUp(@RequestBody AppUser appUser)
    {
        return ResponseEntity.ok().body(userService.signupUser(appUser));
    }

    @PostMapping(AppConstants.API_LOGIN_URL)
    public Response<AppUser> login(@RequestBody AppUser appUser)
    {
        AppUser signedUpUser = userService.signupUser(appUser);
        return responseBuilder.buildSuccess(signedUpUser);
    }

    @GetMapping(AppConstants.API_USER_ALL)
    public Response<List<AppUser>> getUsers()
    {
        List<AppUser> appUsers = userService.getUsers();
        return responseBuilder.buildSuccess(appUsers);
    }


    @PostMapping(AppConstants.API_USER_SAVE)
    public ResponseEntity<AppUser> save(@RequestBody AppUser appUser)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @PostMapping(AppConstants.API_ROLE_SAVE)
    public ResponseEntity<Role> saveRole(@RequestBody Role role)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping(AppConstants.API_USER_TO_ROLE_SAVE)
    public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUser roleToUser)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/role").toUriString());
        userService.addRoleToUser(roleToUser.getUser(), roleToUser.getRole());
        return ResponseEntity.ok().build();
    }

    @GetMapping(AppConstants.API_TOKEN_REFRESH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        tokenRefreshService.refreshToken(request, response);
    }

}