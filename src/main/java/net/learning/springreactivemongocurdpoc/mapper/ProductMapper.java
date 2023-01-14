package net.learning.springreactivemongocurdpoc.mapper;

import net.learning.springreactivemongocurdpoc.dto.Product;
import net.learning.springreactivemongocurdpoc.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * mapper for mapping to entity layer.
 */
public class ProductMapper {
    /**
     * This is to receive a PrpoductRequest and pass to ProdcutEntity object.
     *
     * @param request PrpoductRequest json.
     * @return Single object of product which is saved.
     */
    public static Mono<ProductEntity> mapToProductEntity(Product request, String productId) {
        return Mono.just(
                ProductEntity.builder()
                        .id(productId)
                        .qty(request.getQty())
                        .name(request.getName())
                        .price(request.getPrice())
                        .build());
    }

    public static Mono<Product> mapToProduct(ProductEntity entity) {
        return Mono.just(
                Product.builder()
                        .id(entity.getId())
                        .qty(entity.getQty())
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build());
    }

    public static Flux<Product> mapToAllProduct(ProductEntity entity) {
        return Flux.just(
                Product.builder()
                        .id(entity.getId())
                        .qty(entity.getQty())
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build());
    }

}
