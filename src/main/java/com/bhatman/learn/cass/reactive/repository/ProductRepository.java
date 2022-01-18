package com.bhatman.learn.cass.reactive.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

import com.bhatman.learn.cass.reactive.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCassandraRepository<Product, UUID> {

	@AllowFiltering
	Flux<Product> findByPriceLessThan(float price);

	@AllowFiltering
	Mono<Product> findByName(String name);

}
