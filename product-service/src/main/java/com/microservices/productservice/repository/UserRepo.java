package com.microservices.productservice.repository;

import com.microservices.productservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
