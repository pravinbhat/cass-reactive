package com.bhatman.learn.cass.reactive.product;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductsAndPageId {
    public List<Product> products;
    public String pageID;
}
