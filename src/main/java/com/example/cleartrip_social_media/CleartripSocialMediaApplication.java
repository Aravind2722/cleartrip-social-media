package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CleartripSocialMediaApplication implements CommandLineRunner {

	@Override
	public void run(String... ags) throws InvalidDateOfBirthException {
		System.out.println("started");
//		DateOfBirth dob = DateOfBirthBuilder.build(2024, Month.FEBRUARY, 29);
//		User user = new User();
//		user.setFirstName("Aravind");
//		System.out.println(user.getFirstName());


	}
	public static void main(String[] args) {
		SpringApplication.run(CleartripSocialMediaApplication.class, args);
	}

}
