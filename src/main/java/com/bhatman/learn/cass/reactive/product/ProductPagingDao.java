package com.bhatman.learn.cass.reactive.product;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductPagingDao {

    String allProductsQry = "select * from product";

    @Autowired
    CqlSession session;

    public ProductsAndPageId getProductsFirstPage() {
        return getNextPage(session.execute(allProductsQry));
    }

    public ProductsAndPageId getProductsNextPage(PagingState pagingState) {
        SimpleStatement statement = SimpleStatement.builder(allProductsQry)
                .setPagingState(pagingState.getRawPagingState()).build();
        return getNextPage(session.execute(statement));
    }

    public ProductsAndPageId getNextPage(ResultSet rs) {
        List<Product> products = new ArrayList<>();
        while (rs.getAvailableWithoutFetching() > 0) {
            Row row = rs.one();
            Product product = new Product(row.getUuid(ProductEntity.PRODUCT_ID),
                    row.getString(ProductEntity.PRODUCT_NAME),
                    row.getString(ProductEntity.PRODUCT_TYPE), row.getString(ProductEntity.PRODUCT_LOCATION),
                    row.getFloat(ProductEntity.PRODUCT_PRICE));
            products.add(product);
        }

        PagingState pagingState = rs.getExecutionInfo().getSafePagingState();
        if (rs.isFullyFetched()) {
            return new ProductsAndPageId(products, "");
        }

        return new ProductsAndPageId(products, StringEscapeUtils.escapeHtml4(pagingState.toString()));
    }

}
