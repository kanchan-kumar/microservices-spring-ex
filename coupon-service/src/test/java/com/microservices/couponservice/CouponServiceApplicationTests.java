package com.microservices.couponservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponServiceApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void testGetCouponWithoutAuth_Forbidden() throws Exception {
		mvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {"USER"})
	public void testGetCouponWithAuth_Success() throws Exception {
		mvc.perform(get("/couponapi/coupons/SUPERSALE")).
				andExpect(status().isOk()).andExpect(content().string("{\"id\":1,\"code\":\"SUPERSALE\",\"discount\":10.000,\"expDate\":\"12/12/2020\"}"));
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void testCreateCoupon_withoutCSRF_Forbidden() throws Exception {
		mvc.perform(post("/couponapi/coupons").content("{\"code\":\"SUPERSALE77\",\"discount\":10.000,\"expDate\":\"12/12/2020\"}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void testCreateCoupon_withCSRF_Success() throws Exception {
		mvc.perform(post("/couponapi/coupons").content("{\"code\":\"SUPERSALE77\",\"discount\":10.000,\"expDate\":\"12/12/2020\"}").contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = {"USER"})
	public void testCreateCoupon_NonAdminUser_Forbidden() throws Exception {
		mvc.perform(post("/couponapi/coupons").content("{\"code\":\"SUPERSALE77\",\"discount\":10.000,\"expDate\":\"12/12/2020\"}").contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {"USER"})
	public void testCors() throws Exception {
		mvc.perform(options("/couponapi/coupons").
				header("Access-Control-Request-Method", "POST").
				header("Origin", "https://devxprimetools.web.app")).
				andExpect(status().isOk()).
				andExpect(header().exists("Access-Control-Allow-Origin")).
				andExpect(header().string("Access-Control-Allow-Origin", "*")).
				andExpect(header().exists("Access-Control-Allow-Methods")).
				andExpect(header().string("Access-Control-Allow-Methods", "POST"));

	}

	@Test
	@WithUserDetails("doug@bailey.com")
	public void testGetCouponWithUserDetails_Success() throws Exception {
		mvc.perform(get("/couponapi/coupons/SUPERSALE")).
				andExpect(status().isOk()).andExpect(content().string("{\"id\":1,\"code\":\"SUPERSALE\",\"discount\":10.000,\"expDate\":\"12/12/2020\"}"));
	}
}
