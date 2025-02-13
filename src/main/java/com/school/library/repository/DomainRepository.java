package com.school.library.repository;

import com.school.library.model.Domain;
import org.springframework.data.repository.CrudRepository;

public interface DomainRepository extends CrudRepository<Domain, Long> {
}
