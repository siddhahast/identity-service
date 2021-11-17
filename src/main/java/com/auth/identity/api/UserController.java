package com.auth.identity.api;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.Role;
import com.auth.identity.service.TokenService;
import com.auth.identity.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController
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

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers()
    {
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @PostMapping("/user/save")
    public ResponseEntity<AppUser> save(@RequestBody AppUser appUser)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/user/role")
    public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUser roleToUser)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/role").toUriString());
        userService.addRoleToUser(roleToUser.getUser(), roleToUser.getRole());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        tokenRefreshService.refreshToken(request, response);
    }

}

@Data
class RoleToUser
{
    private String user;
    private String role;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
