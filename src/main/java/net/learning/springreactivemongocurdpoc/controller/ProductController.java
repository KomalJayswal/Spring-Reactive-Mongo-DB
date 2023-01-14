package net.learning.springreactivemongocurdpoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.learning.springreactivemongocurdpoc.dto.Product;
import net.learning.springreactivemongocurdpoc.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * API Controller for Product.
 */
@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET Endpoint to retrieve all the Product Details from DB.
     * @return Product Details
     */
    @GetMapping
    public Flux<Product> getProducts(){
        log.info("Received request to retrieve all the Product Details");
        return productService.getProducts();
    }

    /**
     * GET Endpoint to retrieve the Product Details for a particular product ID from DB.
     * @return Product Response
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> getProduct(@PathVariable("id") String productId) {
        log.info("Received request to retrieve the Product Details with product ID : {}", productId);
        return productService.retriveProduct(productId);
    }

    /**
     * GET Endpoint to retrieve the all the Product Details from DB whose prize is in between the given range
     *
     * @param min prize
     * @param max prize
     * @return Product Details
     */
    @GetMapping("/product-range")
    public Flux<Product> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max")double max){
        return productService.retriveProductInRange(min,max);
    }

    /**
     * POST Endpoint to save Product Details in the DB
     *
     * @param request request json.
     * @return Single object of Product which is saved.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveBooking(@RequestBody Product request) {
        log.info("Received request for saveBooking");
        return productService.saveProduct(request);
    }

    /**
     * PUT Endpoint to update the Product Details of a particular product
     * @param productId a unique ID generated for each Product
     * @return Product Details
     */
    @PutMapping("/update/{id}")
    public Mono<Product> updateProduct(@PathVariable String productId){
        return productService.updateProduct(productId);
    }

    /**
     * DELETE Endpoints to delete a particular Product
     * @param productId a unique ID generated for each Product
     * @return Product Details
     */
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String productId){
        return productService.deleteProduct(productId);
    }
}
