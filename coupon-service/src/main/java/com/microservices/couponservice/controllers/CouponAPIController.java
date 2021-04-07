package com.microservices.couponservice.controllers;

import com.microservices.couponservice.model.Coupon;
import com.microservices.couponservice.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/couponapi")
public class CouponAPIController {

	@Autowired CouponRepository repo;

	//@RequestMapping(value = "/coupons", method = RequestMethod.POST)
	@PostMapping("/coupons")
	@PreAuthorize("hasRole('ADMIN')")
	public Coupon create(@RequestBody Coupon coupon) {
		return repo.save(coupon);
	}

	//@PostAuthorize("returnObject.discount<60")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@RequestMapping(value = "/coupons/{code}", method = RequestMethod.GET)
	public Coupon getCoupon(@PathVariable("code") String code) {
		return repo.findByCode(code);
	}
}
