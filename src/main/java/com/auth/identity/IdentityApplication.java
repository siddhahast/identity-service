package com.auth.identity;

import com.auth.identity.domain.ApiRoleAuth;
import com.auth.identity.domain.AppUser;
import com.auth.identity.domain.Role;
import com.auth.identity.service.ApiRoleAuthService;
import com.auth.identity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@ComponentScan("com.auth.identity")
public class IdentityApplication{

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, ApiRoleAuthService apiRoleAuthService)
	{
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(null, "John", "Travolta", "jtravolta", "jtravolta@crepto.com", "jt1234",new ArrayList<Role>()));
			userService.saveUser(new AppUser(null, "Clint", "Eastwood", "clinte", "clinte@crepto.com", "ce1234",new ArrayList<Role>()));
			userService.saveUser(new AppUser(null, "Sylvestor", "Stallon", "sstallon", "sstallon@crepto.com", "ss1234",new ArrayList<Role>()));
			userService.saveUser(new AppUser(null, "Bruce", "Willis", "bwillis", "bwillis@crepto.com", "bw1234",new ArrayList<Role>()));
			userService.saveUser(new AppUser(null, "Arnold", "Schwarzenegger", "aschwarz", "aschwarz@crepto.com", "as1234",new ArrayList<Role>()));


			userService.addRoleToUser("jtravolta", "ROLE_USER");
			userService.addRoleToUser("clinte", "ROLE_MANAGER");
			userService.addRoleToUser("sstallon", "ROLE_USER");
			userService.addRoleToUser("bwillis", "ROLE_ADMIN");
			userService.addRoleToUser("aschwarz", "ROLE_SUPER_ADMIN");

			apiRoleAuthService.save(new ApiRoleAuth(null, "GET", "/api/users/**", "ROLE_SUPER_ADMIN"));
			apiRoleAuthService.save(new ApiRoleAuth(null, "GET", "/api/user/**", "ROLE_ADMIN"));
			apiRoleAuthService.save(new ApiRoleAuth(null, "GET", "/api/user/**", "ROLE_SUPER_ADMIN"));
			apiRoleAuthService.save(new ApiRoleAuth(null, "POST", "/api/user/save", "ROLE_SUPER_ADMIN"));
			apiRoleAuthService.save(new ApiRoleAuth(null, "POST", "/api/user/save", "ROLE_ADMIN"));
		};
	}

}
