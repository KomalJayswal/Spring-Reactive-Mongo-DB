package net.learning.springreactivemongocurdpoc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.learning.springreactivemongocurdpoc.dto.Product;
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
     * Fetch all Product Details from the DB
     *
     * @return Product Details
     */
    public Flux<Product> getProducts(){
        log.info("Retrieving all Product Details from Database");
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
                                        HttpStatus.NOT_FOUND, "Data not found with product Id")))
                .flatMap(ProductMapper::mapToAllProduct)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Fetch Product Details from the DB for particular Product ID
     *
     * @param productId a unique ID generated for each Product
     * @return Product Details
     */
    public Mono<Product> retriveProduct(String productId){
        log.info("Retrieving Product Details from Database with id : {}", productId);
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
                .flatMap(ProductMapper::mapToProduct)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Fetch Product Details from the DB whose prize is in between min and maz prize
     *
     * @param min prize
     * @param max prize
     * @return Product Details
     */
    public Flux<Product> retriveProductInRange(double min, double max){
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
                                        HttpStatus.NOT_FOUND, "Data not found for prize range between "+min+" and "+max+" prize")));
    }

    /**
     * Save Product Details
     *
     * @param request request json.
     * @return Product Details
     */
    public Mono<Product> saveProduct(Product request){
        log.info("Save Product Details");
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
                .flatMap(ProductMapper::mapToProduct)
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    /**
     * Update Product Details of a particular product ID
     *
     * @param productId a unique ID generated for each Product
     * @return Product Details
     */
    public Mono<Product> updateProduct(String productId){
        log.info("Update Product Details with product id ; {}",productId);
        return productRepository
                .findById(productId)
                .flatMap(productRepository::save)
                .flatMap(ProductMapper::mapToProduct)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while updating product Entity in database")));

    }

    /**
     * Delete a particular Product Details via product ID
     *
     * @param productId a unique ID generated for each Product
     * @return Product Details
     */
    public Mono<Void> deleteProduct(String productId){
        log.info("Delete Product Details with product id ; {}",productId);
        return productRepository.deleteById(productId)
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                        ex ->
                                Mono.error(
                                        new ProductException(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Error occurred while deleting product Entity in database")));
    }
}
