package net.learning.springreactivemongocurdpoc.controller;

import net.learning.springreactivemongocurdpoc.dto.Dto;
import net.learning.springreactivemongocurdpoc.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class Controller {

    @Autowired
    private Service service;

    @GetMapping
    public Flux<Dto> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<Dto> getProduct(@PathVariable String id){
        return service.getProduct(id);
    }

    @GetMapping("/product-range")
    public Flux<Dto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max")double max){
        return service.getProductInRange(min,max);
    }

    @PostMapping
    public Mono<Dto> saveProduct(@RequestBody Mono<Dto> DtoMono){
        System.out.println("controller method called ...");
        return service.saveProduct(DtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<Dto> updateProduct(@RequestBody Mono<Dto> DtoMono,@PathVariable String id){
        return service.updateProduct(DtoMono,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }



}
