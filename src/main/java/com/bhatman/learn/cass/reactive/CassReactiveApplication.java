package com.bhatman.learn.cass.reactive;

import java.util.UUID;

import com.bhatman.learn.cass.reactive.repository.ProductReactiveDao;
import com.bhatman.learn.cass.reactive.utils.MappingUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
@RestController
public class CassReactiveApplication {

	@Autowired
	private ProductReactiveDao prodDao;

	@GetMapping("/products")
	public Flux<Product> getProducts() {
		// return prodRepository.findAll();
		return Flux.from(prodDao.findAll())
				.map(MappingUtils::mapEntityAsProduct);
	}

	@GetMapping("/products/{id}/price/{price}")
	public Flux<Product> getUserByAge(@PathVariable UUID id, @PathVariable float price) {
		return Flux.from(prodDao.findByPriceLessThan(id, price))
				.map(MappingUtils::mapEntityAsProduct);
	}

	@GetMapping("/products/{id}")
	public Mono<Product> getProduct(@PathVariable UUID id) {
		// return prodDao.findByProductId(id);
		return Mono.from(prodDao.findByProductId(id))
				.map(MappingUtils::mapEntityAsProduct);
	}

	public static void main(String[] args) {
		SpringApplication.run(CassReactiveApplication.class, args);
	}
}
