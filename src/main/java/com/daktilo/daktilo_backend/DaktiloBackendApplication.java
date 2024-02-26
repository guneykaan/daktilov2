package com.daktilo.daktilo_backend;

import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
public class DaktiloBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaktiloBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	public CommandLineRunner demoData(UserRepository userRepository){
		return args -> {
			User user = new User();
			user.setFirstName("demo");
			user.setLastName("demo");
			user.setUsername("demo1");
			user.setPassword(bCryptPasswordEncoder().encode("password123"));
			user.setEmail("demo@demo.com");
			user.setPhoneNumber("+231231231312");
			user.setDateJoined(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toInstant().toEpochMilli());
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setEnabled(true);
			user.setCredentialsNonExpired(true);
			user.setRole("ADMIN");

			userRepository.save(user);
		};
	}*/
}
