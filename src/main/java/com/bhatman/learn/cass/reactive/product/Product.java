package com.bhatman.learn.cass.reactive.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private UUID id;
	private String name;
	private String type;
	private String location;
	private Float price;

	public Product(UUID productId) {
		this.id = productId;
	}
}
