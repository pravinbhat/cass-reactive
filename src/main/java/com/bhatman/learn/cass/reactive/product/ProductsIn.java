package com.bhatman.learn.cass.reactive.product;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductsIn {
    private List<UUID> productIds;
    private Product product;
}
