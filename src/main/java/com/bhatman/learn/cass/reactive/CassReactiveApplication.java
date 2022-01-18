package com.bhatman.learn.cass.reactive;

import java.util.UUID;

import com.bhatman.learn.cass.reactive.model.Product;
import com.bhatman.learn.cass.reactive.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class CassReactiveApplication {

	@Autowired
	private ProductRepository prodRepository;

	@GetMapping("/products")
	public Flux<Product> getProducts() {
		return prodRepository.findAll();
	}

	@GetMapping("/products/price/{price}")
	public Flux<Product> getUserByAge(@PathVariable float price) {
		return prodRepository.findByPriceLessThan(price);
	}

	@GetMapping("/products/{id}")
	public Mono<Product> getProduct(@PathVariable UUID id) {
		return prodRepository.findById(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(CassReactiveApplication.class, args);
	}
}
