package com.school.library.repository;

import com.school.library.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    public Admin findByEmail(String email);
}
