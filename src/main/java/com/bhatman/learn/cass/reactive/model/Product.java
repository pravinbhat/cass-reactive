package com.bhatman.learn.cass.reactive.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@PrimaryKey
	public UUID prod_id;
	public String description;
	public String location;
	public String name;
	public float price;

}
