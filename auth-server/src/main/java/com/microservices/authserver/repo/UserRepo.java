package com.microservices.authserver.repo;

import com.microservices.authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
