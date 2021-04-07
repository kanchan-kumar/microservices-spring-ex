package com.microservices.authserver.repo;

import com.microservices.authserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
