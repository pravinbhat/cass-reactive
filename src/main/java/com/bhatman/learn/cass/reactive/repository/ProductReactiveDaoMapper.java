package com.bhatman.learn.cass.reactive.repository;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

import com.bhatman.learn.cass.reactive.model.ProductEntity;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface ProductReactiveDaoMapper {

    @DaoFactory
    ProductReactiveDao productDao(@DaoKeyspace CqlIdentifier keyspace);

    /**
     * Create objects required for this business domain (tables, index, udt) if they
     * do not exist.
     */
    default void createSchema(CqlSession cqlSession) {

        cqlSession.execute(createTable(ProductEntity.PRODUCT_TABLE)
                .ifNotExists()
                .withPartitionKey(ProductEntity.PRODUCT_ID, DataTypes.UUID)
                .withClusteringColumn(ProductEntity.PRODUCT_NAME, DataTypes.TEXT)
                .withColumn(ProductEntity.PRODUCT_TYPE, DataTypes.TEXT)
                .withColumn(ProductEntity.PRODUCT_LOCATION, DataTypes.TEXT)
                .withColumn(ProductEntity.PRODUCT_PRICE, DataTypes.FLOAT)
                .build());
    }

}