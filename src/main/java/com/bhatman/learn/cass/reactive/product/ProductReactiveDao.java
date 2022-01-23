package com.bhatman.learn.cass.reactive.product;

import static com.bhatman.learn.cass.reactive.product.ProductEntity.PRODUCT_ID;
import static com.bhatman.learn.cass.reactive.product.ProductEntity.PRODUCT_PRICE;

import java.util.UUID;

import com.datastax.dse.driver.api.core.cql.reactive.ReactiveResultSet;
import com.datastax.dse.driver.api.mapper.reactive.MappedReactiveResultSet;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

@Dao
public interface ProductReactiveDao {

    /**
     * Find all records in the table.
     * 
     * @implNote Be careful if your data is large or you work with a large cluster,
     *           the function do a full scan cluster but paging is not implemented
     *           in the Angular UI.
     * 
     * @return
     *         the resultSet not exhausted to get the infos
     */
    @Select
    MappedReactiveResultSet<ProductEntity> findAll();

    @Select(customWhereClause = PRODUCT_ID + "= :productId")
    MappedReactiveResultSet<ProductEntity> findByProductId(UUID productId);

    @Select(customWhereClause = PRODUCT_ID + "= :productId" + " and " + PRODUCT_PRICE
            + "<= :price", allowFiltering = true)
    MappedReactiveResultSet<ProductEntity> findByPriceLessThan(UUID productId, float price);

    @Update
    ReactiveResultSet upsert(ProductEntity product);

    @Delete
    ReactiveResultSet delete(ProductEntity product);

}