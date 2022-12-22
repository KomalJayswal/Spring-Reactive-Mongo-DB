package net.learning.springreactivemongocurdpoc.service;

import net.learning.springreactivemongocurdpoc.dto.Dto;
import net.learning.springreactivemongocurdpoc.repository.Repository;
import net.learning.springreactivemongocurdpoc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repository;


    public Flux<Dto> getProducts(){
        return repository.findAll().map(Utils::entityToDto);
    }

    public Mono<Dto> getProduct(String id){
        return repository.findById(id).map(Utils::entityToDto);
    }

    public Flux<Dto> getProductInRange(double min,double max){
        return repository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<Dto> saveProduct(Mono<Dto> DtoMono){
        System.out.println("service method called ...");
        return  DtoMono.map(Utils::dtoToEntity)
                .flatMap(repository::insert)
                .map(Utils::entityToDto);
    }

    public Mono<Dto> updateProduct(Mono<Dto> DtoMono,String id){
        return repository.findById(id)
                .flatMap(p->DtoMono.map(Utils::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(repository::save)
                .map(Utils::entityToDto);

    }

    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
