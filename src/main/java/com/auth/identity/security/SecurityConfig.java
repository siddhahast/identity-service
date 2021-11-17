package com.auth.identity.security;

import com.auth.identity.domain.ApiRoleAuth;
import com.auth.identity.filter.CustomAuthenticationFilter;
import com.auth.identity.filter.CustomAuthorizationFilter;
import com.auth.identity.service.ApiRoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private CustomAuthorizationFilter customAuthorizationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApiRoleAuthService apiRoleAuthService;

    @Value("${authentication.secret}")
    private String tokenSecret;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), tokenSecret);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login", "/api/token/refresh").permitAll();
        configHttpApiRoleMatchers(http);
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }

    private void configHttpApiRoleMatchers(HttpSecurity http) throws Exception
    {
        List<ApiRoleAuth> apiRoleAuthList = apiRoleAuthService.findAll();
        if(apiRoleAuthList==null || apiRoleAuthList.size()==0)
        {
            http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_SUPER_ADMIN");
            http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER_ADMIN");
            http.authorizeRequests().antMatchers(POST, "/api/user/save").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN");
        }
        else
        {
            for(ApiRoleAuth apiRoleAuth : apiRoleAuthList)
            {
                http.authorizeRequests().antMatchers(HttpMethod.resolve(apiRoleAuth.getRequestMethod()),
                        apiRoleAuth.getUrlPattern(), apiRoleAuth.getRole());
            }
        }
    }

}
