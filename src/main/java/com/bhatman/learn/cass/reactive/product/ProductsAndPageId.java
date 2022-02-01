package com.bhatman.learn.cass.reactive.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductsAndPageId {
    private List<Product> products;
    private String nextPageID;
}
