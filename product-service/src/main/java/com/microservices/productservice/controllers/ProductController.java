package com.microservices.productservice.controllers;

import com.microservices.couponservice.model.Coupon;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/productapi")
public class ProductController {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${coupon.service.url}")
	private String couponServiceURL;

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public Product create(@RequestBody Product product) {
		Coupon coupon = restTemplate.getForObject(couponServiceURL + product.getCouponCode(), Coupon.class);
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productRepo.save(product);
	}

	@GetMapping("/products/{id}")
	public Optional<Product> getProduct(@PathVariable Long id) {
		return productRepo.findById(id);
	}
}
