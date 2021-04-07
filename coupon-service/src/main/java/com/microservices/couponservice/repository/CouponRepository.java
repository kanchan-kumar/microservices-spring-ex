package com.microservices.couponservice.repository;

import com.microservices.couponservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Coupon findByCode(String code);
}
