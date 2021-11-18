package com.auth.identity.api;

import com.auth.identity.constants.AppConstants;
import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.RegistrationRequest;
import com.auth.identity.response.Response;
import com.auth.identity.service.RegistrationService;
import com.auth.identity.service.TokenService;
import com.auth.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RegistrationController extends BaseIdentityController
{
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(path = AppConstants.API_REGISTER)
    public Response<RegistrationRequest> register(@RequestBody RegistrationRequest registrationRequest)
    {
        RegistrationRequest newUser = registrationService.register(registrationRequest);
        return responseBuilder.buildSuccess(newUser);
    }

    @PostMapping(path= AppConstants.API_REGISTER_RETRIGGER)
    public Response<AppUser> registerTrigger(@RequestBody RegistrationRequest registrationRequest)
    {
        AppUser appUser = null;
        tokenService.refreshRegistrationToken(appUser);
        return responseBuilder.buildSuccess(appUser);
    }
}