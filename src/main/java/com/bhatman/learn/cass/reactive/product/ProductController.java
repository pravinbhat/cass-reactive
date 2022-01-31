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
import javax.validation.constraints.NotBlank;

import com.bhatman.learn.cass.reactive.utils.MappingUtils;
import com.datastax.oss.driver.api.core.cql.PagingState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

        @Autowired
        private ProductPagingDao productPagingDao;

        @GetMapping(produces = APPLICATION_JSON_VALUE)
        public Flux<Product> getProducts() {
                return Flux.from(productDao.findAll())
                                .map(MappingUtils::mapEntityAsProduct);
        }

        @GetMapping(value = "/page", produces = APPLICATION_JSON_VALUE)
        public ProductsAndPageId getProductsFirstPage() {
                return productPagingDao.getProductsFirstPage();
        }

        @GetMapping(value = "/page/{pageId}", produces = APPLICATION_JSON_VALUE)
        public ProductsAndPageId getProductsPage(@PathVariable("pageId") String pageId) {
                return productPagingDao.getProductsNextPage(PagingState.fromString(pageId));
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
                return upsertProduct(pe);
        }

        @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
        public Mono<Product> upsertProduct(
                        UriComponentsBuilder uc,
                        @PathVariable("id") @NotBlank String id,
                        @RequestBody Product product) {
                Assert.isTrue(UUID.fromString(id).equals(product.getId()),
                                "Product identifier provided does not match the value in path");
                Objects.requireNonNull(product);
                ProductEntity pe = MappingUtils.mapProductAsEntity(product);
                return upsertProduct(pe);
        }

        private Mono<Product> upsertProduct(ProductEntity pe) {
                return Mono.from(productDao.upsert(pe))
                                .map(rr -> pe)
                                .map(MappingUtils::mapEntityAsProduct);
        }

        // This endpoint is not efficient as it uses "in" clause & "in" is not
        // recommended in Cassandra. Use the "bulk_update" endpoint instead
        @PutMapping(value = "/in", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
        public Mono<Product> upsertProductIn(
                        UriComponentsBuilder uc,
                        @RequestBody @NotBlank ProductsIn productsIn) {
                Objects.requireNonNull(productsIn);
                ProductEntity pe = MappingUtils.mapProductAsEntity(productsIn.getProduct());
                return Mono.from(productDao.upsertIn(pe, productsIn.getProductIds())).map(rr -> pe)
                                .map(MappingUtils::mapEntityAsProduct);
        }

        // Use this endpoint for bulk update instead of "in". It calls single updates in
        // parallel
        @PutMapping(value = "/bulk_update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
        public Flux<Product> upsertProductInParallel(
                        UriComponentsBuilder uc,
                        @RequestBody @NotBlank ProductsIn productsIn) {
                Objects.requireNonNull(productsIn);
                ProductEntity pe = MappingUtils.mapProductAsEntity(productsIn.getProduct());
                return Flux.fromStream(productsIn.getProductIds().stream().map(pId -> {
                        pe.setProductId(pId);
                        return pe;
                })).flatMap(pE -> upsertProduct(pE));
        }

        @DeleteMapping("/{id}")
        public Mono<Void> deleteById(
                        @NotBlank @PathVariable("id") @NotBlank UUID id) {
                return Mono.from(productDao.deleteById(id)).and(Mono.empty());
        }

}
