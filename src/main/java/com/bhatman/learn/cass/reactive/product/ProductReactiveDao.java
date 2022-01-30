package com.bhatman.learn.cass.reactive.product;

import static com.bhatman.learn.cass.reactive.product.ProductEntity.PRODUCT_ID;
import static com.bhatman.learn.cass.reactive.product.ProductEntity.PRODUCT_PRICE;

import java.util.List;
import java.util.UUID;

import com.datastax.dse.driver.api.core.cql.reactive.ReactiveResultSet;
import com.datastax.dse.driver.api.mapper.reactive.MappedReactiveResultSet;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;

@Dao
public interface ProductReactiveDao {

    @Select
    MappedReactiveResultSet<ProductEntity> findAll();

    @Select(customWhereClause = PRODUCT_ID + "= :productId")
    MappedReactiveResultSet<ProductEntity> findByProductId(UUID productId);

    @Select(customWhereClause = PRODUCT_ID + "= :productId" + " and " + PRODUCT_PRICE
            + "<= :price", allowFiltering = true)
    MappedReactiveResultSet<ProductEntity> findByPriceLessThan(UUID productId, float price);

    @Update(nullSavingStrategy = NullSavingStrategy.DO_NOT_SET)
    ReactiveResultSet upsert(ProductEntity product);

    @Update(nullSavingStrategy = NullSavingStrategy.DO_NOT_SET, customWhereClause = PRODUCT_ID + " in :productIds")
    ReactiveResultSet upsertIn(ProductEntity product, List<UUID> productIds);

    @Delete(entityClass = ProductEntity.class)
    ReactiveResultSet delete(ProductEntity product);

    @Delete(entityClass = ProductEntity.class)
    ReactiveResultSet deleteById(UUID productId);

}