package com.bhatman.learn.cass.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;

@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
public class CassReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassReactiveApplication.class, args);
	}
}
