package com.microservices.couponservice.security;

public interface SecurityService {

	public boolean login(String userName, String password);
}
