package com.bhatman.learn.cass.reactive;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	public UUID id;
	public String name;
	public String type;
	public String location;
	public float price;

	public Product(UUID productId) {
		this.id = productId;
	}
}
