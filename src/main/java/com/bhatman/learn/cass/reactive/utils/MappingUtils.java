package com.bhatman.learn.cass.reactive.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bhatman.learn.cass.reactive.product.Product;
import com.bhatman.learn.cass.reactive.product.ProductEntity;

import org.springframework.lang.NonNull;

public class MappingUtils {

    /** Date format to be used in the REST API. (which can only use Strings). */
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /** Hide constructor to enforce static usage. */
    private MappingUtils() {
    }

    /**
     * Convert a {@link LocalDate} to a String using the forma yyyy/MM/dd.
     */
    public static String localDate2String(@NonNull LocalDate source) {
        return FORMATTER.format(source);
    }

    /**
     * Parse a String as a {@link LocalDate} expecting formay yyyy/MM/dd.
     */
    public static LocalDate string2LocalDate(@NotBlank String source) {
        return LocalDate.from(FORMATTER.parse(source));
    }

    public static Product mapEntityAsProduct(@NotNull ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getProductId());
        product.setName(entity.getName());
        product.setType(entity.getType());
        product.setLocation(entity.getLocation());
        product.setPrice(entity.getPrice());
        return product;
    }

    public static ProductEntity mapProductAsEntity(@Valid Product product) {
        ProductEntity pe = new ProductEntity();
        pe.setProductId(product.getId());
        pe.setName(product.getName());
        pe.setType(product.getType());
        pe.setLocation(product.getLocation());
        pe.setPrice(product.getPrice());
        return pe;
    }

}