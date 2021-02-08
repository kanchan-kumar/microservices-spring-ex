package com.cavisson.microservices.currencyexchangeservice.controller;

import com.cavisson.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.cavisson.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	@Autowired
	private Environment env;

	@Autowired
	private CurrencyExchangeRepository repository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to
	) {
		logger.info("Request Received with values {}, {}", from, to);
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " and " + to);
		}

		String port = env.getProperty("local.server.port");
		currencyExchange.setEnviroment(port);

		return currencyExchange;
	}

}
