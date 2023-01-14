package net.learning.springreactivemongocurdpoc.mapper;

import net.learning.springreactivemongocurdpoc.dto.ProductRequest;
import net.learning.springreactivemongocurdpoc.dto.ProductResponse;
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
    public static Mono<ProductEntity> mapToProductEntity(ProductRequest request, String productId) {
        return Mono.just(
                ProductEntity.builder()
                        .id(productId)
                        .qty(request.getQuantity())
                        .name(request.getName())
                        .price(request.getPrice())
                        .build());
    }

    public static Mono<ProductResponse> mapToProductResponse(ProductEntity entity) {
        return Mono.just(
                ProductResponse.builder()
                        .productId(entity.getId())
                        .quantity(entity.getQty())
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build());
    }

    public static Flux<ProductResponse> mapToAllProductEntity(ProductEntity entity) {
        return Flux.just(
                ProductResponse.builder()
                        .productId(entity.getId())
                        .quantity(entity.getQty())
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build());
    }

    public static Mono<ProductEntity> mapUpdateProductEntity(ProductRequest updateRequest, ProductEntity entity) {
        return Mono.just(
                ProductEntity.builder()
                        .id(entity.getId())
                        .qty(updateRequest.getQuantity())
                        .name(updateRequest.getName())
                        .price(updateRequest.getPrice())
                        .build());
    }
}
