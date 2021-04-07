package com.microservices.couponservice.controllers;

import com.microservices.couponservice.model.Coupon;
import com.microservices.couponservice.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@Controller
public class CouponController {

	@Autowired
	private CouponRepository couponRepository;

	/*@GetMapping("/")
	public String index() {
		return "index";
	}*/

	@GetMapping("/showCreateCoupons")
	@PreAuthorize("hasRole('ADMIN')")
	//@RolesAllowed("ADMIN")
	//@Secured("ADMIN")
	public String showCreateCoupons() {
		return "createCoupons";
	}

	@PostMapping("/saveCoupon")
	public String saveCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return "createResponse";
	}

	@GetMapping("/showGetCoupons")
	public String showGetCoupons() {
		return "getCoupons";
	}

	@PostMapping("/getCoupon")
	public ModelAndView getCoupon(String code) {
		ModelAndView mav = new ModelAndView("couponDetails");
		mav.addObject(couponRepository.findByCode(code));
		return mav;
	}

}
