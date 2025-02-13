package com.school.library.service;

import com.oracle.svm.core.annotate.Inject;
import com.school.library.model.Admin;
import com.school.library.model.RegisterDto;
import com.school.library.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Admin adminUser = adminRepository.findByEmail(email);

        if(adminUser != null){
            return User.withUsername(adminUser.getEmail())
                    .password(adminUser.getPassword())
                    .roles(adminUser.getRole())
                    .build();
        }

        return null;
    }

    @PostConstruct
    public void init() {
        if(adminRepository!=null){
        List<Admin> adminList = (List<Admin>) adminRepository.findAll();
        if(adminList.isEmpty()) {
            RegisterDto registerDto = new RegisterDto();
            registerDto.setEmail("scgen2bv@gmail.com");
            registerDto.setPassword("admin123");
            registerDto.setConfirmPassword("admin123");

            var bCryptEncoder = new BCryptPasswordEncoder();

            Admin newUser = new Admin();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setRole("admin");
            newUser.setCreatedAt(new Date());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
            adminRepository.save(newUser);
        }
        }
    }

}
