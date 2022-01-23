package com.bhatman.learn.cass.reactive.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bhatman.learn.cass.reactive.product.ProductReactiveDao;
import com.bhatman.learn.cass.reactive.product.ProductReactiveDaoMapper;
import com.bhatman.learn.cass.reactive.product.ProductReactiveDaoMapperBuilder;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@Configuration
public class CassandraConfig {

	/** Logger for the class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);

	/**
	 * This flag will help us decide between 2 configurations files
	 * `application-astra.conf` or `application-local.conf`
	 * 
	 * Why not simply injecting the filename ? If we are using local
	 * we also create the keyspace.
	 */

	@Value("${cassreactive.cassandra.local-keyspace.create}")
	private boolean localKeyspaceCreate;

	@Value("${cassreactive.cassandra.local-keyspace.name}")
	private String localKeyspaceName;

	/**
	 * Create the Singleton {@link CqlSession} used everywhere to
	 * access the Cassandra DB.
	 */
	@Bean
	public CqlSession cqlSession() {

		LOGGER.info("Reading configuration");
		Map<String, String> env = System.getenv();
		if (env != null) {
			for (String key : env.keySet()) {
				LOGGER.info("key=" + key + ", value=" + env.get(key));
			}
		}

		DriverConfigLoader configReader;
		CqlSession cqlSession;

		// the file 'application-astra.local' contains all configuration keys to work
		// locally
		configReader = DriverConfigLoader.fromClasspath("application-local.conf");
		cqlSession = CqlSession.builder().withConfigLoader(configReader).build();
		// If we are working locally (docker) we may need to create the keypace
		if (localKeyspaceCreate) {
			// cqlSession.execute(localKeyspaceCql);
		}
		cqlSession.execute("use " + localKeyspaceName);

		// Create schema upfront
		return cqlSession;
	}

	@Bean
	public ProductReactiveDao productDao(CqlSession cqlSession) {
		ProductReactiveDaoMapper productMapper = new ProductReactiveDaoMapperBuilder(cqlSession).build();
		productMapper.createSchema(cqlSession);
		ProductReactiveDao productDao = productMapper.productDao(cqlSession.getKeyspace().get());
		return productDao;
	}

}