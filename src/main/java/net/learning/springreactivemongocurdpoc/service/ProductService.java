package net.learning.springreactivemongocurdpoc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.learning.springreactivemongocurdpoc.dto.ProductRequest;
import net.learning.springreactivemongocurdpoc.dto.ProductResponse;
import net.learning.springreactivemongocurdpoc.exceptions.ProductException;
import net.learning.springreactivemongocurdpoc.mapper.ProductMapper;
import net.learning.springreactivemongocurdpoc.repository.ProductRepository;
import org.springframework.data.domain.Range;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class for the implementataion of the CURD operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final IdGeneratorService idGeneratorService;

    /**
     * Fetch all ProductResponse Details from the DB
     *
     * @return ProductResponse Details
     */
    public Flux<ProductResponse> getProducts(){
        log.info("Retrieving all ProductResponse Details from Database");
        return productRepository
                .findAll()
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while retrieving product Entity from database ")))
                .switchIfEmpty(
                        Mono.error(
                                new ProductException(
                                        HttpStatus.NOT_FOUND, "Data Not Found : Collection is empty")))
                .flatMap(ProductMapper::mapToAllProductEntity)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Fetch ProductResponse Details from the DB for particular ProductResponse ID
     *
     * @param productId a unique ID generated for each ProductResponse
     * @return ProductResponse Details
     */
    public Mono<ProductResponse> retriveProduct(String productId){
        log.info("Retrieving ProductResponse Details from Database with id : {}", productId);
        return productRepository
                .findById(productId)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while retrieving product Entity from database with id :: "
                                                        + productId)))
                .switchIfEmpty(
                        Mono.error(
                                new ProductException(
                                        HttpStatus.NOT_FOUND, "Data not found with product Id : " + productId)))
                .flatMap(ProductMapper::mapToProductResponse)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Fetch ProductResponse Details from the DB whose prize is in between min and maz prize
     *
     * @param min prize
     * @param max prize
     * @return ProductResponse Details
     */
    public Flux<ProductResponse> retriveProductInRange(double min, double max){
        return productRepository
                .findByPriceBetween(Range.closed(min,max))
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while retrieving product Entity from database ")))
                .switchIfEmpty(
                        Mono.error(
                                new ProductException(
                                        HttpStatus.NOT_FOUND, "Data not found for prize range between "+min+" and "+max+" prize")))
                .flatMap(ProductMapper::mapToAllProductEntity)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Save ProductResponse Details
     *
     * @param request request json.
     * @return ProductResponse Details
     */
    public Mono<ProductResponse> saveProduct(ProductRequest request){
        log.info("Save ProductResponse Details");
        return idGeneratorService.getProductId()
                .flatMap(productId -> ProductMapper.mapToProductEntity(request, productId))
                .flatMap(productRepository::save)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while storing product Entity in database")))
                .flatMap(ProductMapper::mapToProductResponse)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Update ProductResponse Details of a particular product ID
     *
     * @param productId a unique ID generated for each ProductResponse
     * @return ProductResponse Details
     */
    public Mono<ProductResponse> updateProduct(ProductRequest productRequest,String productId){
        log.info("Update ProductResponse Details with product id : {}",productId);

        return productRepository
                .findById(productId)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while retrieving product Entity from database ")))
                .switchIfEmpty(
                        Mono.error(
                                new ProductException(
                                        HttpStatus.NOT_FOUND, "Data not found with product Id")))
                .flatMap(productEntity -> ProductMapper.mapUpdateProductEntity(productRequest,productEntity))
                .flatMap(productRepository::save)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while updating product Entity in database")))
                .flatMap(ProductMapper::mapToProductResponse)
                .doOnError(ex -> log.error(ex.getMessage()));

    }

    /**
     * Delete a particular ProductResponse Details via product ID
     *
     * @param productId a unique ID generated for each ProductResponse
     * @return ProductResponse Details
     */
    public Mono<String> deleteProduct(String productId) {
        log.info("Delete ProductResponse Details with product id ; {}", productId);
        return productRepository
                .deleteById(productId)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while deleting product Entity in database")))
                .thenReturn("Product is Deleted successfully !");
    }
}
