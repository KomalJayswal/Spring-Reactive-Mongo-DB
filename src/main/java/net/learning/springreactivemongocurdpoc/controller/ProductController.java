package net.learning.springreactivemongocurdpoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.learning.springreactivemongocurdpoc.dto.ProductRequest;
import net.learning.springreactivemongocurdpoc.dto.ProductResponse;
import net.learning.springreactivemongocurdpoc.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * API Controller for ProductResponse.
 */
@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET Endpoint to retrieve all the ProductResponse Details from DB.
     * @return ProductResponse Details
     */
    @GetMapping
    public Flux<ProductResponse> getProducts(){
        log.info("Received request to retrieve all the ProductResponse Details");
        return productService.getProducts();
    }

    /**
     * GET Endpoint to retrieve the ProductResponse Details for a particular product ID from DB.
     * @return ProductResponse Response
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> getProduct(@PathVariable("id") String productId) {
        log.info("Received request to retrieve the ProductResponse Details with product ID : {}", productId);
        return productService.retriveProduct(productId);
    }

    /**
     * GET Endpoint to retrieve the all the ProductResponse Details from DB whose prize is in between the given range
     *
     * @param min prize
     * @param max prize
     * @return ProductResponse Details
     */
    @GetMapping("/product-range")
    public Flux<ProductResponse> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max")double max){
        return productService.retriveProductInRange(min,max);
    }

    /**
     * POST Endpoint to save ProductResponse Details in the DB
     *
     * @param request request json.
     * @return Single object of ProductResponse which is saved.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> saveBooking(@RequestBody ProductRequest request) {
        log.info("Received request for save Product");
        return productService.saveProduct(request);
    }

    /**
     * PUT Endpoint to update the ProductResponse Details of a particular product
     * @param productId a unique ID generated for each ProductResponse
     * @return ProductResponse Details
     */
    @PutMapping("/{id}")
    public Mono<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable("id") String productId){
        log.info("Received request for updating Product");
        return productService.updateProduct(request, productId);
    }

    /**
     * DELETE Endpoints to delete a particular ProductResponse
     * @param productId a unique ID generated for each ProductResponse
     * @return ProductResponse Details
     */
    @DeleteMapping("/{id}")
    public Mono<String> deleteProduct(@PathVariable("id") String productId){
        log.info("Received request for deleting Product with productId : {},",productId);
        return productService.deleteProduct(productId);
    }
}
