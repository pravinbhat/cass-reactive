package com.bhatman.learn.cass.reactive.model;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@CqlName(ProductEntity.PRODUCT_TABLE)
public class ProductEntity {
	public static final String PRODUCT_TABLE = "product";
	public static final String PRODUCT_ID = "product_id";
	public static final String PRODUCT_NAME = "name";
	public static final String PRODUCT_TYPE = "type";
	public static final String PRODUCT_LOCATION = "location";
	public static final String PRODUCT_PRICE = "price";

	@PartitionKey
	@CqlName(PRODUCT_ID)
	public UUID productId;

	@CqlName(PRODUCT_NAME)
	public String name;

	@CqlName(PRODUCT_TYPE)
	public String type;

	@CqlName(PRODUCT_LOCATION)
	public String location;

	@CqlName(PRODUCT_PRICE)
	public float price;

}
