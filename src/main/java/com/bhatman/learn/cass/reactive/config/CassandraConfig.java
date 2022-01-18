package com.bhatman.learn.cass.reactive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace}")
	private String keySpace;

	@Value("${spring.data.cassandra.local-datacenter}")
	private String dataCenter;

	@Override
	protected String getKeyspaceName() {
		return keySpace;
	}

	@Override
	protected String getLocalDataCenter() {
		return dataCenter;
	}

}
