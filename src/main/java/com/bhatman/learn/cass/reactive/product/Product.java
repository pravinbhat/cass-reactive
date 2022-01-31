package com.bhatman.learn.cass.reactive.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	public UUID id;
	public String name;
	public String type;
	public String location;
	public Float price;

	public Product(UUID productId) {
		this.id = productId;
	}
}
