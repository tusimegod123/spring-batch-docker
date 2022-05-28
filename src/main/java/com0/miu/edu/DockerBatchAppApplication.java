package com0.miu.edu;

import com0.miu.edu.model.Role;
import com0.miu.edu.model.User;
import com0.miu.edu.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DockerBatchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerBatchAppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));


			userService.saveUser(new User(null, "Godwin Tusime", "godwin", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Samuel James", "james", "2222", new ArrayList<>()));

			userService.addRoleToUser("godwin", "ADMIN");
			userService.addRoleToUser("james", "USER");



		};
	}

}
