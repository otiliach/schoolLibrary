package com.school.library;

import com.school.library.repository.AdminRepository;
import com.school.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {
	@Autowired
	private AdminRepository adminRepository;

	@Bean
	public CommandLineRunner seedDB(){
		return args -> {
			AdminService adminService = new AdminService(adminRepository);
			adminService.init();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
