package com.bhatman.learn.cass.reactive.product;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import com.bhatman.learn.cass.reactive.utils.MappingUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@CrossOrigin(methods = { PUT, POST, GET, OPTIONS, DELETE, PATCH }, maxAge = 3600, allowedHeaders = { "x-requested-with",
        "origin", "content-type", "accept" }, origins = "*")
public class ProductController {

    @Autowired
    private ProductReactiveDao productDao;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Flux<Product> getProducts() {
        return Flux.from(productDao.findAll())
                .map(MappingUtils::mapEntityAsProduct);
    }

    @GetMapping(value = "/{id}/price/{price}", produces = APPLICATION_JSON_VALUE)
    public Flux<Product> getUserByAge(@PathVariable UUID id, @PathVariable float price) {
        return Flux.from(productDao.findByPriceLessThan(id, price))
                .map(MappingUtils::mapEntityAsProduct);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<Product> getProduct(@PathVariable UUID id) {
        return Mono.from(productDao.findByProductId(id))
                .map(MappingUtils::mapEntityAsProduct);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<Product> createProduct(UriComponentsBuilder uc, @RequestBody @Valid Product product) {
        product.setId(UUID.randomUUID());
        Objects.requireNonNull(product);
        ProductEntity pe = MappingUtils.mapProductAsEntity(product);
        return Mono.from(productDao.upsert(pe))
                .map(rr -> pe)
                .map(MappingUtils::mapEntityAsProduct);
    }

}